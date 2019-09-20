package com.mrrobot.suitbridge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogClass extends AppCompatDialogFragment
{
    private EditText reg_no;
    private dialogListner mDialogListner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.message_reference_dialog,null);

        builder.setView(view)
                .setTitle("OOPS!")
                .setMessage("There was a problem, to fix the problem add the last 2 numbers of your registration no with the registration no of the person you want to send the message to.")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String registration = reg_no.getText().toString();
                        mDialogListner.applyText(registration);
                    }
                });

        reg_no = view.findViewById(R.id.regno_dialog_edittext);
     return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            mDialogListner = (dialogListner) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "Must Input the Added Registration no to proceed!");
        }
    }

    public interface dialogListner
    {
        void applyText(String registrtion);
    }
}
