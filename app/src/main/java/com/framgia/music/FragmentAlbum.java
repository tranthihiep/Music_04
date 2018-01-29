package com.framgia.music;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by trant on 20/01/2018.
 */

public class FragmentAlbum extends Fragment implements OnItemClickListenner {
    private RecyclerView mRecyclerAlbum;
    private ArrayList<Album> mArrayAlbum;
    private AdapterAlbum mAdapterAlbum;
    private AlbumDataHelper mDataAlbum;
    private EditText mEdtNameAlbum;
    private ImageView mBtnAddAlbum;
    private Dialog mDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mRecyclerAlbum = (RecyclerView) view.findViewById(R.id.recyclerAlbum);
        mEdtNameAlbum = (EditText) view.findViewById(R.id.edtFagSearchAlbum);
        mBtnAddAlbum = (ImageView) view.findViewById(R.id.btnFagAddAlbum);
        mDataAlbum = new AlbumDataHelper(getContext());
        mArrayAlbum = new ArrayList<>();
        setRecyclerAlbum();
        mBtnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddAlbum();
            }
        });
        return view;
    }

    private void setRecyclerAlbum() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerAlbum.setLayoutManager(layoutManager);
        mRecyclerAlbum.setItemAnimator(new DefaultItemAnimator());
        fillListAlbumToList();
    }

    private void fillListAlbumToList() {
        mArrayAlbum = mDataAlbum.queryAlbum();
        mAdapterAlbum = new AdapterAlbum(mArrayAlbum, getContext(), this);
        mRecyclerAlbum.setAdapter(mAdapterAlbum);
        mAdapterAlbum.notifyDataSetChanged();
    }

    private void dialogAddAlbum() {
        mDialog = new Dialog(getContext());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_add_album);
        final EditText edtAlbum = mDialog.findViewById(R.id.edtDialog);
        Button btnAdd = mDialog.findViewById(R.id.btnAddDialog);
        Button btnCancel = mDialog.findViewById(R.id.btnCancelDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtAlbum.getText().toString().equals("")) {
                    Toast.makeText(getContext(), R.string.please_input_album_name,
                            Toast.LENGTH_SHORT).show();
                } else {
                    mDataAlbum.insertAlbumIntoTableAlbum(edtAlbum.getText().toString());
                    fillListAlbumToList();
                    mDialog.dismiss();
                }
            }
        });
        mDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeleteRowAlbum:
                //TODO
                break;
            case R.id.btnEditRowAlbum:
                //TODO
                break;
        }
    }
}

