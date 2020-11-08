import component.*;

public class Login {
  public static void main(String[] args) {
    WinForm winForm = new WinForm("WINDOWS窗口");

    winForm
        .add(new Picture("LOGO图片", 10, 20, 330, 50))
        .add(new Button("登录", 10, 240, 80, 25))
        .add(new Button("注册", 100, 240, 80, 25));
    Frame frame = new Frame("FRAME1", 10, 80, 330, 150);
    frame
        .add(new Lable("用户名", 10, 10, 80, 25))
        .add(new TextBox("文本框", 100, 10, 165, 25))
        .add(new Lable("密码", 10, 50, 80, 25))
        .add(new PasswordBox("密码框", 100, 50, 165, 25))
        .add(new CheckBox("复选框", 10, 90, 80, 25))
        .add(new Lable("记住用户名", 50, 90, 80, 25))
        .add(new LinkLable("忘记密码", 150, 90, 80, 25));
    winForm.add(frame);
    winForm.print().repaint();
  }
}
