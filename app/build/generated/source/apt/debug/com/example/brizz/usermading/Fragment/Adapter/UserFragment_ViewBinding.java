// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.Fragment.Adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserFragment_ViewBinding implements Unbinder {
  private UserFragment target;

  @UiThread
  public UserFragment_ViewBinding(UserFragment target, View source) {
    this.target = target;

    target.profilUsername = Utils.findRequiredViewAsType(source, R.id.profil_username, "field 'profilUsername'", TextView.class);
    target.profilNotelp = Utils.findRequiredViewAsType(source, R.id.profil_notelp, "field 'profilNotelp'", TextView.class);
    target.profilEmail = Utils.findRequiredViewAsType(source, R.id.profil_email, "field 'profilEmail'", TextView.class);
    target.profilImage = Utils.findRequiredViewAsType(source, R.id.profil_image, "field 'profilImage'", CircleImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profilUsername = null;
    target.profilNotelp = null;
    target.profilEmail = null;
    target.profilImage = null;
  }
}
