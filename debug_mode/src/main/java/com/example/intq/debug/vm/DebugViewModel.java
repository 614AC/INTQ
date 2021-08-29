package com.example.intq.debug.vm;

import androidx.databinding.ObservableField;

import com.example.intq.common.core.WDApplication;
import com.example.intq.common.core.WDViewModel;
import com.example.intq.common.util.shared.SharedUtils;
import com.example.intq.debug.util.ClassScannerUtil;

import java.util.Map;

import me.ele.uetool.UETool;

/**
 * author dingtao
 * date   1/14/21 4:47 PM
 * desc
 */
public class DebugViewModel extends WDViewModel {

    public final static String SWITCH_UETOOL = "switch_uetool";
    public ObservableField<Boolean> uetoolCheck = new ObservableField<>();
    public ObservableField<String> classList = new ObservableField<>();

    @Override
    protected void create() {
        super.create();
        boolean ue = SharedUtils.getBoolen(SWITCH_UETOOL);
        uetoolCheck.set(ue);

        updataModule();
    }

    private void updataModule() {
        Map<String,String> map = ClassScannerUtil.scan(WDApplication.getContext(), "com.vc.wd", "BuildConfig");
        if (map!=null){
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String,String> entry:map.entrySet()) {
                builder.append(entry.getKey());
                builder.append("\n");
            }
            classList.set(builder.toString());
        }
    }

    public void uetool() {
        if (uetoolCheck.get()) {
            UETool.showUETMenu();
        } else {
            UETool.dismissUETMenu();
        }
        SharedUtils.put(SWITCH_UETOOL, uetoolCheck.get());
    }
}
