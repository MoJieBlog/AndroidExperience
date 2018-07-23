package com.lzp.experience.test.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.experience.R;
import com.lzp.experience.main.adapter.MainTwoAdapter;
import com.lzp.experience.test.SheetAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Li Xiaopeng on 18/6/26.
 */
public class MBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.rcv)
    RecyclerView rcv;
    Unbinder unbinder;
    private Context context;

    private BottomSheetBehavior behavior;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRcv();
    }

    private void initRcv() {
        rcv.setLayoutManager(new LinearLayoutManager(context));
        SheetAdapter sheetAdapter = new SheetAdapter(context);
        rcv.setAdapter(sheetAdapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(String.valueOf(i + 1));
        }

        sheetAdapter.refreshData(list);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
