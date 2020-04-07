package com.example.atilagapps.hellixdatamanager.TuitionFess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.atilagapps.hellixdatamanager.R;

public class AmountDialogueClass extends DialogFragment {

    private EditText amountEdit;
    private AmountDialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.payment_dialogue,null);

        builder.setView(v)
                .setTitle("Amount")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String amount=amountEdit.getText().toString();
                        listener.getAmount(amount);

                    }
                });
        amountEdit=v.findViewById(R.id.amountPaidEditText);

        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(AmountDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }

    public interface AmountDialogueListener{
        void getAmount(String amount);
    }


}
