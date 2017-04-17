/** Index Nested Loop Join by Elan Markov
CSE 510 Phase 3 Group 12
Task 1 */
package iterator;

import global.*;
import heap.*;
import diskmgr.*;
import bufmgr.*;
import index.*;
import btree.*;
import java.io.*;
import java.util.ArrayList;

public class IndexNestedJoin extends Iterator {
	private static boolean OK = true;
	private static boolean FAIL = false;
	private Heapfile joinHeap;
	private static Scan EdgeScan;
	private static BTFileScan indexSearch;
	private boolean status;
	private AttrType      _in1[],  _in2[];
        private   int        in1_len, in2_len;
  	private   Iterator  outer;
  	private   short t2_str_sizescopy[];
  	private   CondExpr OutputFilter[];
  	private   CondExpr RightFilter[];
  	private   int        n_buf_pgs;        // # of buffer pages available.
  	private   Tuple     outer_tuple, inner_tuple;
  	private   Tuple     Jtuple;           // Joined tuple
  	private   FldSpec   perm_mat[];
  	private   int        nOutFlds;
/** Constructor for join of a Tuple and edge or node.
Parameters as in NestedLoopsJoins class
IndexNestedJoin as an argument for previous tuple set.
CondExpr for condition on edge/node
typejoin as per switch statement below. */
	public IndexNestedJoin(AttrType    in1[],    
			   int     len_in1,           
			   short   t1_str_sizes[],
			   AttrType    in2[],         
			   int     len_in2,           
			   short   t2_str_sizes[],   
			   int     amt_of_mem,        
			   Iterator     am1,          
			   String relationName,      
			   CondExpr outFilter[],      
			   CondExpr rightFilter[],    
			   FldSpec   proj_list[],
			   int        n_out_flds,
			   IndexNestedJoin arg, CondExpr condition, int typejoin) 
		throws Exception {
		switch(typejoin) {
		case 0:
			// tuple-sourcenode join (node condition)
		case 1:
			// tuple-destnode join (node condition)
		case 2:
			// tuple-edge join (edge condition)
		default:
			// error
		}
		//NestedJoinNode(arg, condition);
	}
/** Constructor for join of a Tuple and edge or node.
Parameters as in NestedLoopsJoins class
CondExpr for condition on edge/nodes
typejoin as per switch statement below. */
	public IndexNestedJoin(
				AttrType    in1[],    
			   int     len_in1,           
			   short   t1_str_sizes[],
			   AttrType    in2[],         
			   int     len_in2,           
			   short   t2_str_sizes[],   
			   int     amt_of_mem,        
			   Iterator     am1,          
			   String relationName,      
			   CondExpr outFilter[],      
			   CondExpr rightFilter[],    
			   FldSpec   proj_list[],
			   int        n_out_flds,
			   CondExpr condition,
			   int typejoin) 
		throws Exception {
	      _in1 = new AttrType[in1.length];
	      _in2 = new AttrType[in2.length];
	      System.arraycopy(in1,0,_in1,0,in1.length);
	      System.arraycopy(in2,0,_in2,0,in2.length);
	      in1_len = len_in1;
	      in2_len = len_in2;
	      
	      
	      outer = am1;
	      t2_str_sizescopy =  t2_str_sizes;
	      inner_tuple = new Tuple();
	      Jtuple = new Tuple();
	      OutputFilter = outFilter;
	      RightFilter  = rightFilter;
	      
	      n_buf_pgs    = amt_of_mem;
	      
	      AttrType[] Jtypes = new AttrType[n_out_flds];
	      short[]    t_size;
	      
	      perm_mat = proj_list;
      	      nOutFlds = n_out_flds;

	      try {
		t_size = TupleUtils.setup_op_tuple(Jtuple, Jtypes,
						   in1, len_in1, in2, len_in2,
						   t1_str_sizes, t2_str_sizes,
						   proj_list, nOutFlds);
	      }catch (TupleUtilsException e){
		throw new NestedLoopException(e,"TupleUtilsException is caught by IndexNestedJoin.java");
	      }
	      
	      
	      
	      try {
		  joinHeap = new Heapfile(relationName);
		  
	      }
	      catch(Exception e) {
		throw new NestedLoopException(e, "Create new heapfile failed.");
	      }
		switch(typejoin) {
		case 0:
			sourceEdgeJoin(condition);
			break;
		case 1:
			destEdgeJoin(condition);
			break;
		case 2:
			edgeSourceJoin(condition);
			break;
		case 3:
			edgeDestJoin(condition);
			break;
		default: 
			status = FAIL;
			close();
		}
	}
	public static void sourceEdgeJoin (CondExpr edgeCond) 
		throws NestedLoopException {
		// Node JOIN (source, edge_condition) Edge
		try {
			EdgeScan = SystemDefs.JavabaseDB.getEdgeOpenScan();
	        }
	        catch(Exception e){
		    throw new NestedLoopException(e, "openScan failed on edges");
	        }
		try {
			indexSearch = SystemDefs.JavabaseDB.getSourceScan();
	        }
	        catch(Exception e){
		    throw new NestedLoopException(e, "failed to access source node index");
	        }
		boolean done = false;
		Edge nextEdge;
		Node nextNode = null;
		while(!done) {
			//nextEdge = EdgeScan.getNext(null);
			if(//condExpr is true
				true) {
				//nextNode = indexSearch.get_next();
				while(nextNode != null) {
					// joinHeap.add(new Tuple(nextNode, nextEdge));
					//nextNode = indexSearch.get_next();
					
				} 			
			}
		}
	}
	public static void destEdgeJoin (CondExpr edgeCond) {
		// Node JOIN (dest, edge_condition) Edge
		// Perform Join using all edges that satisfy the condition
	}
	public static void edgeSourceJoin (CondExpr nodeCond) {
		// Edge JOIN (source, node_condition) Node
		// Perform Join using all nodes that satisfy the condition
	}
	public static void edgeDestJoin (CondExpr nodeCond) {
		// Edge JOIN (dest, node_condition) Node
		// Perform Join using all nodes that satisfy the condition
	}
/** Get the next tuple in the joined heap. Returns null if none remaining. 
The entire join should have been performed by the above methods. This will just get them from 
the relevant heap file. */
    public Tuple get_next()
    throws IOException,
	   JoinsException,
	   IndexException,
	   InvalidTupleSizeException,
	   InvalidTypeException, 
	   PageNotReadException,
	   TupleUtilsException, 
	   PredEvalException,
	   SortException,
	   LowMemException,
	   UnknowAttrType,
	   UnknownKeyTypeException,
	   Exception
    {
	Tuple sup = new Tuple();
	return sup;
    } 
/** Close the joined file. */
    public void close() 
	   throws IOException, 
	   JoinsException, 
	   SortException,
	   IndexException{
		System.out.println ("\n"); 
		if (status != OK) {
			//bail out
			System.err.println ("*** Error in closing ");
			Runtime.getRuntime().exit(1);
		}
	}
}