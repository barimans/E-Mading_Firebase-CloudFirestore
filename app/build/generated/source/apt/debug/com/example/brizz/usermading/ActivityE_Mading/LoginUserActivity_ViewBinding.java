// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.ActivityE_Mading;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginUserActivity_ViewBinding implements Unbinder {
  private LoginUserActivity target;

  private View view7f08002c;

  private View view7f08002b;

  @UiThread
  public LoginUserActivity_ViewBinding(LoginUserActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginUserActivity_ViewBinding(final LoginUserActivity target, View source) {
    this.target = target;

    View view;
    target.emailLogin = Utils.findRequiredViewAsType(source, R.id.email_login, "field 'emailLogin'", TextInputEditText.class);
    target.passLogin = Utils.findRequiredViewAsType(source, R.id.pass_login, "field 'passLogin'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'btnLogin' and method 'onLoginClicked'");
    target.btnLogin = Utils.castView(view, R.id.btn_login, "field 'btnLogin'", Button.class);
    view7f08002c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLoginClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_gosignup, "field 'btnGosignup' and method 'onSignUpClicked'");
    target.btnGosignup = Utils.castView(view, R.id.btn_gosignup, "field 'btnGosignup'", Button.class);
    view7f08002b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSignUpClicked();
      }
    });
    target.logoMagazine = Utils.findRequiredViewAsType(source, R.id.logo_magazine, "field 'logoMagazine'", ImageView.class);
    target.tvEmading = Utils.findRequiredViewAsType(source, R.id.tv_emading, "field 'tvEmading'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginUserActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.emailLogin = null;
    target.passLogin = null;
    target.btnLogin = null;
    target.btnGosignup = null;
    target.logoMagazine = null;
    target.tvEmading = null;
    target.progressBar = null;

    view7f08002c.setOnClickListener(null);
    view7f08002c = null;
    view7f08002b.setOnClickListener(null);
    view7f08002b = null;
  }
}
