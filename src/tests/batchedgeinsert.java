/* Batch Edge Insertion By Shalmali Bhoir
 *
 * Accepts edge data file name and graph database name from command line
 * Creates database with given name
 * Reads data file given and inserts the edges from the file in the database
 */
package tests;

import java.io.*;
import java.util.*;

import btree.AddFileEntryException;
import btree.ConstructPageException;
import btree.GetFileEntryException;
import global.*;
import diskmgr.*;
import heap.*;

/** Class for the batch edge insert test.
Passes parameters to handler and runs program. */
public class batchedgeinsert {
	/** Passes parameters to handler for current DB and outputs result of test. */
	public boolean runTests(String edgeFile) throws FileNotFoundException, IOException, SpaceNotAvailableException, HFBufMgrException, InvalidSlotNumberException, InvalidTupleSizeException, HFException, HFDiskMgrException, GetFileEntryException, ConstructPageException, AddFileEntryException {
		System.out.println ("\nRunning Batch Edge Insert tests....\n");
		BatchEdgeInsertHandler queries = SystemDefs.JavabaseDB.getBatchEdgeInsertHandler();
		boolean _pass = queries.test1(edgeFile);
		System.out.print ("\n" + "... Batch Edge Insert tests ");
		System.out.print (_pass==true ? "completed successfully." : "failed.");
		System.out.print (".\n\n");
		return _pass;
	}
}
