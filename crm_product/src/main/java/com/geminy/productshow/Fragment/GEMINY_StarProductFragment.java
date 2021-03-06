package com.geminy.productshow.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geminy.productshow.Activity.GEMINY_MainActivity;
import com.geminy.productshow.Activity.GEMINY_ProductDetailInfosActivity;
import com.geminy.productshow.DAO.GEMINY_ProductDAO;
import com.geminy.productshow.Model.GEMINY_Tb_product;
import com.geminy.productshow.R;
import com.geminy.productshow.GEMINY_mAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hatsune Miku on 2015/8/10.
 */
public class GEMINY_StarProductFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_STAR="section_star";
    public static final String PRO_NAME="pro_name";
    public static final String UID="UID";

    private ListView mListView;
    private GEMINY_mAdapter mAdapter;


    public GEMINY_StarProductFragment(){}

    public GEMINY_StarProductFragment newInstance(int sectionNumber,int sectionstar,String uid) {
        GEMINY_StarProductFragment fragment = new GEMINY_StarProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ARG_SECTION_STAR, sectionstar);
        args.putString(UID, uid);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View starProductFragmentView = (View)inflater.inflate(R.layout.geminy_fragment_main, container, false);
        mListView=(ListView)starProductFragmentView.findViewById(R.id.default_fragment_listview);
        initView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.list_item_name);
                String pro_name = tv.getText().toString();
                Bundle bundle = new Bundle();
                String uid = getArguments().getString(UID);
                Intent intent = new Intent(getActivity(), GEMINY_ProductDetailInfosActivity.class);
                bundle.putString(PRO_NAME, pro_name);
                bundle.putString(UID, uid);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        return starProductFragmentView;

    }
    private void initView(){
        GEMINY_ProductDAO productInfo=new GEMINY_ProductDAO(getActivity());
        int star=getArguments().getInt(ARG_SECTION_STAR);
        List<GEMINY_Tb_product> listInfo=productInfo.getByStar(star);
        List<String> names=new ArrayList<String>();
        List<String> types=new ArrayList<String>();
        List<String> imgIds=new ArrayList<String>();
        List<String> nums=new ArrayList<String>();

        for(GEMINY_Tb_product tb_product:listInfo){
            names.add(tb_product.getName());
            types.add(tb_product.getType());
            imgIds.add(tb_product.getPicture());
            nums.add("库存"+tb_product.getNum()+"个");
        }
        mListView.setAdapter(new GEMINY_mAdapter(getActivity(), names, types, imgIds, nums));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((GEMINY_MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));

    }
    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
