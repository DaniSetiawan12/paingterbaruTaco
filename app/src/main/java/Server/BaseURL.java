package Server;

public class BaseURL {

        public static String baseUrl = "http://192.168.18.9:10/";


        //input history
        public static String hapusHistory = baseUrl + "histori/hapus/";
        public static String history = baseUrl + "perintah/lihat-history/";
        public static String historyall = baseUrl + "perintah/lihat-history";

        //users
        public static String registrasi = baseUrl + "user/registrasi";
        public static String MenuLogin = baseUrl + "user/login";
        public static String konfirmasiSandi = baseUrl + "user/konfirm-sandi";
        public static String controlling = baseUrl + "perintah/update-perintah/";
}

