package mail;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SendMailUtil {

    //qq�˻����÷��������˿ڣ��˻�������
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    private static final String FROM_ADD = "313101128@qq.com";
    private static final String FROM_PSW = "riqvynareiphcbch";

      //163�˻�
//    private static final String HOST = "smtp.163.com";
//    private static final String PORT = "465"; //����465  994
//    private static final String FROM_ADD = "18090050095@163.com";
//    private static final String FROM_PSW = "liujie5201314";
//    private static final String TO_ADD = "2584770373@qq.com";


    public static void send(final ArrayList<File> fileArrayList, String toAdd){
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,fileArrayList);
            }
        }).start();
    }


    public static void send(String toAdd){
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    @NonNull
    private static MailInfo creatMail(String toAdd) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // ��������ַ
        mailInfo.setPassword(FROM_PSW);// ������������
        mailInfo.setFromAddress(FROM_ADD); // ���͵�����
        mailInfo.setToAddress(toAdd); // �����ĸ��ʼ�ȥ
        mailInfo.setSubject("Hello"); // �ʼ�����
        mailInfo.setContent("Android ����"); // �ʼ��ı�
        return mailInfo;
    }

}
