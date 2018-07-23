package binding.com.demo.ui.home;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.binding.model.adapter.pager.FragmentAdapter;
import com.binding.model.data.util.JsonDeepUtil;
import com.binding.model.layout.pager.PagerModel;
import com.binding.model.model.ModelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import binding.com.demo.R;
import binding.com.demo.base.arouter.ArouterUtil;
import binding.com.demo.base.util.InfoEntity;
import binding.com.demo.databinding.ActivityHomeBinding;
import binding.com.demo.inject.api.Api;
import binding.com.demo.inject.qualifier.manager.ActivityFragmentManager;
import io.reactivex.Observable;

import static binding.com.demo.inject.component.ActivityComponent.Router.address;

@ModelView(R.layout.activity_home)
public class HomeModel extends PagerModel<HomeActivity,ActivityHomeBinding,HomeEntity> {
    @Inject HomeModel(@ActivityFragmentManager FragmentManager fragmentManager) {
        super(new FragmentAdapter<>(fragmentManager));
    }

//    @Inject Api api;
    @Override
    public void attachView(Bundle savedInstanceState, HomeActivity homeActivity) {
        super.attachView(savedInstanceState, homeActivity);
        List<HomeEntity> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new HomeEntity());
        }
        setRcHttp((offset1, refresh) -> Observable.fromIterable(list).toList().toObservable());
        currentItem.set(0);
    }

    public void onClick(View view){
        jsonTest();
    }


    private String json = "{\n" +
            "  \"sites\": {\n" +
            "    \"site\": [[[\n" +
            "      {\n" +
            "        \"id\": \"1\",\n" +
            "        \"name\": \"菜鸟教程\",\n" +
            "        \"url\": \"www.runoob.com\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"2\",\n" +
            "        \"name\": \"菜鸟工具\",\n" +
            "        \"url\": \"c.runoob.com\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"3\",\n" +
            "        \"name\": \"Google\",\n" +
            "        \"url\": \"www.google.com\"\n" +
            "      }\n" +
            "    ]]]\n" +
            "  }\n" +
            "}";

    public void jsonTest(){
        DataEntity dataEntity = JsonDeepUtil.parse(json,DataEntity.class);
        DataEntity.SitesBean bean = dataEntity.getSites();
    }

    public static class DataEntity{


        private SitesBean sites;

        public SitesBean getSites() {
            return sites;
        }

        public void setSites(SitesBean sites) {
            this.sites = sites;
        }

        public static class SitesBean {
            private List<SiteBean[][]> site;

            public  List<SiteBean[][]>  getSite() {
                return site;
            }

            public void setSite( List<SiteBean[][]>  site) {
                this.site = site;
            }

            public static class SiteBean {
                /**
                 * id : 1
                 * name : 菜鸟教程
                 * url : www.runoob.com
                 */

                private String id;
                private String name;
                private String url;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }


    @Override
    public void onRightClick(View view) {
        super.onRightClick(view);
        ArouterUtil.navigation(address);
    }





}
