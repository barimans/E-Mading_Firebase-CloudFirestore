// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.ActivityE_Mading;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  private View view7f08002f;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(final SignUpActivity target, View source) {
    this.target = target;

    View view;
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progressBar'", ProgressBar.class);
    target.emailSignup = Utils.findRequiredViewAsType(source, R.id.email_signup, "field 'emailSignup'", TextInputEditText.class);
    target.passSignup = Utils.findRequiredViewAsType(source, R.id.pass_signup, "field 'passSignup'", TextInputEditText.class);
    target.repassSignup = Utils.findRequiredViewAsType(source, R.id.repass_signup, "field 'repassSignup'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_sign_up, "field 'btnSignUp' and method 'onViewClicked'");
    target.btnSignUp = Utils.castView(view, R.id.btn_sign_up, "field 'btnSignUp'", Button.class);
    view7f08002f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.emailSignup = null;
    target.passSignup = null;
    target.repassSignup = null;
    target.btnSignUp = null;

    view7f08002f.setOnClickListener(null);
    view7f08002f = null;
  }
}
