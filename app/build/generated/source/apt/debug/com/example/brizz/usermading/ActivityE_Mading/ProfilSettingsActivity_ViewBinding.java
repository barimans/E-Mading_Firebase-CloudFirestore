// Generated code from Butter Knife. Do not modify!
package com.example.brizz.usermading.ActivityE_Mading;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.brizz.usermading.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfilSettingsActivity_ViewBinding implements Unbinder {
  private ProfilSettingsActivity target;

  private View view7f08003a;

  private View view7f08002e;

  @UiThread
  public ProfilSettingsActivity_ViewBinding(ProfilSettingsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfilSettingsActivity_ViewBinding(final ProfilSettingsActivity target, View source) {
    this.target = target;

    View view;
    target.profilToolbar = Utils.findRequiredViewAsType(source, R.id.profil_toolbar, "field 'profilToolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.circle_image, "field 'circleImage' and method 'onViewClicked'");
    target.circleImage = Utils.castView(view, R.id.circle_image, "field 'circleImage'", CircleImageView.class);
    view7f08003a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.userProfil = Utils.findRequiredViewAsType(source, R.id.user_profil, "field 'userProfil'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_save_profil, "field 'btnSaveProfil' and method 'onSaveProfilClicked'");
    target.btnSaveProfil = Utils.castView(view, R.id.btn_save_profil, "field 'btnSaveProfil'", Button.class);
    view7f08002e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSaveProfilClicked();
      }
    });
    target.setupProgress = Utils.findRequiredViewAsType(source, R.id.setup_progress, "field 'setupProgress'", ProgressBar.class);
    target.phoneSignup = Utils.findRequiredViewAsType(source, R.id.phone_signup, "field 'phoneSignup'", TextInputEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfilSettingsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profilToolbar = null;
    target.circleImage = null;
    target.userProfil = null;
    target.btnSaveProfil = null;
    target.setupProgress = null;
    target.phoneSignup = null;

    view7f08003a.setOnClickListener(null);
    view7f08003a = null;
    view7f08002e.setOnClickListener(null);
    view7f08002e = null;
  }
}
