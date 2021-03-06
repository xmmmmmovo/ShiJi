package com.androidlab.shiji.fragment_search_result;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.MapContent;
import com.androidlab.shiji.bean.Scatter3Ddata;
import com.androidlab.shiji.helper.MapContentAdapter;
import com.github.abel533.echarts.Geo;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.code.CoordinateSystem;
import com.github.abel533.echarts.code.Roam;
import com.github.abel533.echarts.data.MapData;
import com.github.abel533.echarts.data.ScatterData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Map;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;

import java.util.ArrayList;
import java.util.List;

//地图
public class FragmentMap extends Fragment {
    private View view;

    private WebView searchMap;
    private RecyclerView recyclerView;
    private static String keyword1;
    private List<MapContent> bookToAu;
    List<MapContent> datas;

    public static FragmentMap newInstance(String keyword) {
        Bundle args = new Bundle();
        keyword1 = keyword;
        FragmentMap fragment = new FragmentMap();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_2, container, false);
        searchMap = (WebView) view.findViewById(R.id.search_map);
        searchMap.getSettings().setAllowFileAccess(true);
        searchMap.getSettings().setJavaScriptEnabled(true);
        searchMap.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        searchMap.getSettings().setSupportZoom(true);
        searchMap.getSettings().setDisplayZoomControls(true);
        //searchMap.loadUrl("file:///android_asset/aMaptest.html");
        searchMap.loadUrl("file:///android_asset/aNewMaptest.html");

        initData();

        /**
         * js方法的调用必须在html页面加载完成之后才能调用。
         * 用webview加载html还是需要耗时间的，必须等待加载完，在执行代用js方法的代码。
         */
        searchMap.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showMap();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_map);
        initRec();
        return view;
    }

    private void initData() {
        bookToAu = new ArrayList<>();
        bookToAu.add(new MapContent("三国志","",1,1));
        bookToAu.add(new MapContent("魏书","",1,1));
        bookToAu.add(new MapContent("南史","",1,1));
        bookToAu.add(new MapContent("南齐书","",1,1));
        bookToAu.add(new MapContent("","",1,1));
        bookToAu.add(new MapContent("","",1,1));
        bookToAu.add(new MapContent("","",1,1));
        bookToAu.add(new MapContent("","",1,1));
        bookToAu.add(new MapContent("","",1,1));
    }

    private void initRec() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//设置Adapter
        List<MapContent> datas = new ArrayList<>();
        if(keyword1.equals("史记")){
            datas.add(new MapContent(R.drawable.num1, R.drawable.bshiji, "史记", "作者司马迁（前145年或前135年-不可考），字子长，夏阳(今陕西韩城南)人。西汉史学家、散文家。司马谈之子，任太史令，因替李陵败降之事辩解而受宫刑，后任中书令。发奋继续完成所著史籍，被后世尊称为史迁、太史公、历史之父。"));
            datas.add(new MapContent(R.drawable.num2, R.drawable.bbeiqishu, "北齐书", "作者李百药（564年—648年），字重规，博陵安平（今河北安平县）人。隋唐时期大臣、史学家、诗人，隋朝内史令李德林之子。"));
            datas.add(new MapContent(R.drawable.num3, R.drawable.bchenshu, "陈书", "作者姚思廉（557年—637年），字简之，一说名简，字思廉，吴兴（今浙江湖州）人。其父姚察于陈朝灭亡后到隋朝做官，迁至北方，故两《唐书》中《姚思廉传》称其为京兆万年（今陕西长安县）人。唐朝初期史学家"));
            datas.add(new MapContent(R.drawable.num4, R.drawable.bbeishi, "北史", "作者李延寿，男，生卒年待考。唐代史学家，相州（今河南安阳）人。贞观年间，做过太子典膳丞、崇贤馆学士，后任御史台主簿，官至符玺郎，兼修国史。"));
        }else if(keyword1.equals("陛下")){
            datas.add(new MapContent(R.drawable.num1, R.drawable.zhanguoce, "战国策", "《荆轲刺秦王》：“秦武阳奉地图匣，以此进至陛下。”古时帝王的卫士就在陛下两侧进行戒备。后演变为臣子对帝王的尊称。"));
            datas.add(new MapContent(R.drawable.num2, R.drawable.shiji, "史记", "《史记·秦始皇本纪》：今陛下兴义兵，诛残贼，平定天下，海内为郡县，法令由一统，自上古以来未尝有，五帝所不及。"));
            datas.add(new MapContent(R.drawable.num3, R.drawable.shuowenjiezi, "说文解字", "《说文》：升高阶也。从阜，坒声。”本义是台阶。特指皇宫的台阶。"));

        }else if(keyword1.equals("三国志")){
            datas.add(new MapContent(R.drawable.bsanguozhi, R.drawable.bshiji, "三国志", "《三国志》是由西晋陈寿所著，记载中国三国时代历史的断代史，同时也是二十四史中评价最高的“前四史”之一。"));
            datas.add(new MapContent(R.drawable.num2, R.drawable.bbeiqishu, "三国演义", "《三国演义》，作者一般被认为是元末明初的罗贯中，是中国第一部长篇历史章回小说，是四大名著中唯一根据历史事实改编之小说。明末清初文学家、戏曲家李渔有言曰：“演义一书之奇，足以使学士读之而快，委巷不学之人读之而亦快；英雄豪杰读之而快，凡夫俗子读之而亦快。”"));
            datas.add(new MapContent(R.drawable.num3, R.drawable.bjinshu, "晋书", "《晋书》于唐朝贞观二十二年（公元648年）写成，中国的二十四史之一，唐房玄龄等人合著，作者共二十一人。记载的历史上起三国时期司马懿早年，下至东晋恭帝元熙二年（420年）刘裕废晋帝自立，以宋代晋。"));

        }
        else{
            datas.add(new MapContent(R.drawable.num1, R.drawable.bshiji, "史记", "作者司马迁（前145年或前135年-不可考），字子长，夏阳(今陕西韩城南)人。西汉史学家、散文家。司马谈之子，任太史令，因替李陵败降之事辩解而受宫刑，后任中书令。发奋继续完成所著史籍，被后世尊称为史迁、太史公、历史之父。"));
            datas.add(new MapContent(R.drawable.num2, R.drawable.bbeiqishu, "北齐书", "作者李百药（564年—648年），字重规，博陵安平（今河北安平县）人。隋唐时期大臣、史学家、诗人，隋朝内史令李德林之子。"));
            datas.add(new MapContent(R.drawable.num3, R.drawable.bchenshu, "陈书", "作者姚思廉（557年—637年），字简之，一说名简，字思廉，吴兴（今浙江湖州）人。其父姚察于陈朝灭亡后到隋朝做官，迁至北方，故两《唐书》中《姚思廉传》称其为京兆万年（今陕西长安县）人。唐朝初期史学家"));
            datas.add(new MapContent(R.drawable.num4, R.drawable.bbeishi, "北史", "作者李延寿，男，生卒年待考。唐代史学家，相州（今河南安阳）人。贞观年间，做过太子典膳丞、崇贤馆学士，后任御史台主簿，官至符玺郎，兼修国史。"));
        }

        MapContentAdapter recycleAdapter = new MapContentAdapter(datas);
        recyclerView.setAdapter(recycleAdapter);

    }

    private void showMap() {
        //searchMap.loadUrl("javascript:clear()");
/**
        GsonOption option = new GsonOption();
        option.backgroundColor("#FFFFFF");
        Title title = new Title();
        title.setText(keyword1 + "On The Map");
        title.setLeft("center");
        option.title(title);


        Geo geo = new Geo();
        geo.map("china");
        geo.roam(Roam.move);
        option.geo(geo);

        Scatter scatter = new Scatter();
        scatter.name("测试点");
        scatter.coordinateSystem(CoordinateSystem.geo);
        List<ScatterData> mapdata = new ArrayList<>();
        List<Object> vals = new ArrayList<>();
        vals.add("12");
        vals.add("45");
        ScatterData data1 = new ScatterData(110.45, 35.47, 1);
        //data1.setValue(vals);
        ScatterData data2 = new ScatterData(120.2, 30.3, 2);
        //data2.setValue(vals);
        ScatterData data3 = new ScatterData(120.8, 39.5, 3);
        //data3.setValue(vals);
        ScatterData data4 = new ScatterData(110.2, 28.8, 4);
        //data4.setValue(vals);
        mapdata.add(data1);
        mapdata.add(data2);
        mapdata.add(data3);
        mapdata.add(data4);
        scatter.setData(mapdata);
        scatter.symbolSize(18);
        ItemStyle itemStyle = new ItemStyle();
        Normal normal = new Normal();
        normal.formatter("{@[2]}");
        normal.show(true);
        itemStyle.normal(normal);
        scatter.setLabel(itemStyle);
        option.series(scatter);
*/
        // option.series(map);

//GsonUtil.format(this);
        List<Scatter3Ddata> map3Ddata = new ArrayList<>();
        if(keyword1.equals("史记")){
            map3Ddata.add(new Scatter3Ddata("陕西韩城","110.449","35.4825","150"));
            map3Ddata.add(new Scatter3Ddata("河北安平县","115.525","38.240","145"));
            map3Ddata.add(new Scatter3Ddata("浙江湖州","120.0945","30.8991","140"));
            map3Ddata.add(new Scatter3Ddata("河南安阳","114.3995","36.1037","135"));
            map3Ddata.add(new Scatter3Ddata("min","120","30","100"));
        }else if(keyword1.equals("三国志")){
            map3Ddata.add(new Scatter3Ddata("四川南充","106.1172","30.8432","150"));
            map3Ddata.add(new Scatter3Ddata("山西太原","112.557","37.8768","140"));
            map3Ddata.add(new Scatter3Ddata("山东济南","117.5579","36.776","130"));
            map3Ddata.add(new Scatter3Ddata("min","120","30","100"));
        }else if(keyword1.equals("陛下")){
            map3Ddata.add(new Scatter3Ddata("江苏徐州","117.2923","34.210","150"));
            map3Ddata.add(new Scatter3Ddata("陕西韩城","110.449","35.4825","140"));
            map3Ddata.add(new Scatter3Ddata("河南漯河","114.10","33.5923","130"));
            map3Ddata.add(new Scatter3Ddata("min","120","30","100"));
        }else{
            map3Ddata.add(new Scatter3Ddata("绍兴","120.02","30.2","150"));
            map3Ddata.add(new Scatter3Ddata("绍兴","105.02","30.2","140"));
            map3Ddata.add(new Scatter3Ddata("绍兴","110.02","40.2","130"));
            map3Ddata.add(new Scatter3Ddata("min","120","30","100"));
        }

        //Log.d("MapFragment",GsonUtil.format(map3Ddata));
        //searchMap.loadUrl("javascript:loadEcharts('" + option.toString() + "')");
        searchMap.loadUrl("javascript:loadEcharts('" + GsonUtil.format(map3Ddata) + "')");
    }
}
