package cn.chestnut.mvvm.teamworker.module.directory;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.chestnut.mvvm.teamworker.BR;
import cn.chestnut.mvvm.teamworker.R;
import cn.chestnut.mvvm.teamworker.databinding.FragmentDirectoryBinding;
import cn.chestnut.mvvm.teamworker.http.ApiResponse;
import cn.chestnut.mvvm.teamworker.http.AppCallBack;
import cn.chestnut.mvvm.teamworker.http.HttpUrls;
import cn.chestnut.mvvm.teamworker.http.RequestManager;
import cn.chestnut.mvvm.teamworker.main.activity.MainActivity;
import cn.chestnut.mvvm.teamworker.main.adapter.BaseListViewAdapter;
import cn.chestnut.mvvm.teamworker.main.common.BaseFragment;
import cn.chestnut.mvvm.teamworker.model.Team;
import cn.chestnut.mvvm.teamworker.module.team.BuildTeamActivity;
import cn.chestnut.mvvm.teamworker.module.team.ViewTeamInformationActivity;

/**
 * Copyright (c) 2018, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2018/4/10 9:41:31
 * Description：通讯录Fragment
 * Email: xiaoting233zhang@126.com
 */

public class DirectoryFragment extends BaseFragment {

    private FragmentDirectoryBinding binding;

    private BaseListViewAdapter<Team> workMyTeamAdapter;

    private ArrayList<Team> teamList;

    @Override
    protected void setBaseTitle(TextView titleView) {
        titleView.setText("通讯录");
    }

    @Override
    protected void addContainerView(ViewGroup viewGroup, LayoutInflater inflater) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_directory, viewGroup, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initView();
        addListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        teamList = new ArrayList<>();
        getMyTeam();

    }

    /**
     * 初始化界面
     */
    private void initView() {
        workMyTeamAdapter = new BaseListViewAdapter<>(R.layout.item_team, BR.team, teamList);
        binding.lvMyTeam.setAdapter(workMyTeamAdapter);
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        binding.llBuildTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), BuildTeamActivity.class), MainActivity.REQUEST_CODE_BUILD_TEAM);
            }
        });

        binding.lvMyTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewTeamInformationActivity.class);
                intent.putExtra("team", teamList.get(position));
                getActivity().startActivity(intent);
            }
        });
    }

    private void getMyTeam() {
        RequestManager.getInstance(getActivity()).executeRequest(HttpUrls.GET_MY_TEAMS, null, new AppCallBack<ApiResponse<List<Team>>>() {
            @Override
            public void next(ApiResponse<List<Team>> response) {
                if (response.isSuccess()) {
                    if (response.getData().size() > 0) {
                        teamList.addAll(response.getData());
                        workMyTeamAdapter.notifyDataSetChanged();
                    }else {
                        binding.tvNoTeam.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void error(Throwable error) {

            }

            @Override
            public void complete() {

            }
        });
    }

}
