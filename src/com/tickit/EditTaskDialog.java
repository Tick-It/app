package com.tickit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class EditTaskDialog extends DialogFragment  {
	
	private Context context;
	private String task;
	private String duedate;
	
	public EditTaskDialog(){
    }
	
	public EditTaskDialog(Context context, String task, String duedate){
        this.context = context;
        this.task = task;
        this.duedate = duedate;
        }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View v = inflater.inflate(R.layout.edit_dialog, null);
	    
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           });      
	    		
	    EditText task = (EditText) v.findViewById(R.id.dialog_task);
		task.setText((CharSequence) task);
		
		EditText duedate = (EditText) v.findViewById(R.id.dialog_duedate);
		duedate.setText((CharSequence) duedate);
		
		
	    return builder.create();
	}

}
