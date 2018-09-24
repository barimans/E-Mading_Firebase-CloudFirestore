// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.ActivityE_Mading;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import com.github.chrisbanes.photoview.PhotoView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SinglePageActivity_ViewBinding implements Unbinder {
  private SinglePageActivity target;

  @UiThread
  public SinglePageActivity_ViewBinding(SinglePageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SinglePageActivity_ViewBinding(SinglePageActivity target, View source) {
    this.target = target;

    target.singleUserimage = Utils.findRequiredViewAsType(source, R.id.single_userimage, "field 'singleUserimage'", CircleImageView.class);
    target.singleUsername = Utils.findRequiredViewAsType(source, R.id.single_username, "field 'singleUsername'", TextView.class);
    target.singleImage = Utils.findRequiredViewAsType(source, R.id.single_image, "field 'singleImage'", PhotoView.class);
    target.singleTag = Utils.findRequiredViewAsType(source, R.id.single_tag, "field 'singleTag'", TextView.class);
    target.singleDesc = Utils.findRequiredViewAsType(source, R.id.single_desc, "field 'singleDesc'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SinglePageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.singleUserimage = null;
    target.singleUsername = null;
    target.singleImage = null;
    target.singleTag = null;
    target.singleDesc = null;
  }
}
