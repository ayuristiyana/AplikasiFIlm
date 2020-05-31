package com.aa183.kemaladewi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_POSTER = "Poster";
    private final static String KEY_SUTRADARA = "Sutradara";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_POSTER + " TEXT, " + KEY_SUTRADARA + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_PENULIS + " TEXT, "
                + KEY_SINOPSIS + " TEXT," + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_POSTER, dataFilm.getPoster());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_POSTER, dataFilm.getPoster());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
    }


    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_POSTER, dataFilm.getPoster());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_PENULIS, dataFilm.getPenulis());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});

        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7),
                        csr.getString(8)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }


        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        // Menambah data film ke-1
        try {
            tempDate = sdFormat.parse("24/04/2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film1 = new Film(
                idFilm,
                "Avengers : Endgame",
                tempDate,
                storeImageFile(R.drawable.avengers1 ),
                "Anthony dan Joe Russo",
                "Robert Downey Jr, Chris Evans, Mark Ruffalo, Chris Hemsworth, Scarlett Johansson, Jeremy Renner, Don Cheadle, Don Cheadle, Paul Rudd, Brie Larson, Karen Gillan, Danai Gurira, Benedict Wong, Jon Favreau, Bradley Cooper, Gwyneth Paltrow, dan Josh Brolin\n",
                "Christopher Markus dan Stephen McFeely",
                "Dua puluh tiga hari setelah Thanos menggunakan Infinity Gauntlet untuk menghancurkan setengah dari seluruh kehidupan di alam semesta, Carol Danvers menyelamatkan Nebula dan Tony Stark, yang terdampar di luar angkasa setelah pertarungan mereka dengan Thanos. Mereka bergabung dengan Natasha Romanoff, Bruce Banner, Steve Rogers, Rocket, Thor, Pepper Potts, dan James Rhodes di Bumi. Kelompok itu menemukan Thanos disebuah planet terpencil dan berencana untuk merebut dan menggunakan batu Infinity dan membalikkan tindakannya, tetapi Thanos telah menghancurkan batu tersebut untuk mencegah digunakan kembali. Thor yang marah akhirnya memenggal Thanos.\n" +
                        "Lima tahun kemudian, Scott Lang keluar dari alam kuantum. Ia pergi ke markas Avengers, dimana ia menjelaskan kepada Romanoff dan Rogers bahwa ia hanya melalui lima jam di alam kuantum, bukan lima tahun. dan ia berteori bahwa alam kuantum dapat memungkinkan mereka melakukan perjalanan waktu ke masa lalu, Ketiganya meminta Stark — yang sekarang membesarkan seorang putri, Morgan, bersama Pepper — agar mereka mengambil batu Infinity dari masa lalu dan menggunakannya untuk mengembalikan tindakan Thanos di masa sekarang. Stark menolak gagasan itu karena takut kehilangan Morgan, tetapi mengalah setelah merenungkan Peter Parker yang hancur. Stark bekerja bersama Banner, yang telah menyatukan kepintarannya dan kekuatan Hulk, untuk mendesain perangkat untuk melakukan perjalanan waktu. Banner memperingatkan bahwa mengubah masa lalu tidak mengubah masa kini dan perubahan sedikitpun dapat menciptakan realita yang berbeda. Ia dan Rocket pergi ke rumah baru pengungsi Asgard di Norwegia - New Asgard - untuk merekrut Thor, yang kini menjadi pemabuk gemuk, putus asa karena kegagalannya dalam menghentikan Thanos. Di Tokyo, Romanoff merekrut Clint Barton, yang kini menjadi seorang pejuang jalanan setelah keluarganya menghilang.\n" +
                        "Banner, Lang, Rogers, dan Stark pergi ke Kota New York di tahun 2012. Banner mengunjungi Sanctum Sanctorum dan meyakinkan Ancient One untuk memberinya Batu Waktu. Rogers berhasil mengambil Batu Pikiran, namun Stark dan Lang tidak sengaja membiarkan Loki dari tahun 2012 untuk kabur dengan Batu Angkasa. Rogers dan Stark pergi ke markas S.H.I.E.L.D. di tahun 1970, dimana Stark mengambil versi awal dari Batu Angkasa dan bertemu dengan Howard Stark muda saat mengambilnya, sedangkan Rogers mengambil beberapa Partikel Pym dari Hank Pym untuk kembali ke masa kini. Rocket dan Thor pergi ke Asgard di tahun 2013, mengekstrak Batu Realita dari Jane Foster dan mengambil palu Thor, Mjolnir. Nebula dan Rhodes pergi ke Morag di tahun 2014 dan mencuri Batu Kekuatan sebelum Peter Quill dapat mencurinya. Rhodes kembali ke masa kini dengan Batu Kekuatan, namun Nebula tidak sadarkan diri ketika implan cyberneticnya berhubungan dengan dirinya dari masa lalu. Melalui hubungan ini, Thanos 2014 mempelajari mengenai keberhasilannya di masa depan dan keinginan Avengers untuk membalikkannya. Thanos menangkap Nebula 2023 dan mengirim Nebula 2014 ke masa kini untuk menggantikannya. Barton dan Romanoff pergi ke Vormir, dimana sang penjaga Batu Jiwa, Red Skull, memberitahu mereka bahwa batu tersebut hanya dapat diambil dengan mengorbankan seseorang yang mereka cintai. Romanoff mengorbankan dirinya, dan Barton mengambil Batu Jiwa.\n" +
                        "Kembali ke masa kini, para Avengers memasangkan batu-batu tersebut ke sarung tangan yang dibuat oleh Stark, dimana Banner menggunakannya untuk mengembalikan semua yang Thanos hilangkan. Nebula 2014 menggunakan mesin waktu untuk membawa Thanos 2014 dan kapal perangnya ke masa kini, dan menyerang markas Avengers, berencana untuk menghancurkan dan membangun kembali alam semesta dengan Batu Infinity. Nebula 2023 meyakinkan Gamora 2014 untuk mengkhianati Thanos dan membunuh Nebula 2014. Stark, Rogers, dan Thor bertarung melawan Thanos namun kewalahan. Thanos memanggil pasukannya untuk menghancurkan Bumi, namun Stephen Strange yang kembali datang dengan penyihir lainnya, para Avengers, dan Guardians of the Galaxy yang hilang, pasukan Wakanda dan Asgard, dan para Ravagers untuk melawan Thanos dan pasukannya bersama Danvers, yang menghancurkan kapal Thanos ketika ia tiba. setelah mengalahkan para pahlawan, Thanos merebut sarung tangan tersebut, namun Stark mencuri batunya dan menggunakannya untuk menghilangkan Thanos dan Pasukannya, lalu mati karena energi yang dikeluarkan oleh batu tersebut.\n" +
                        "Setelah pemakaman Stark, Thor menunjuk Valkyrie sebagai penguasa Asgard Baru dan bergabung dengan Guardians of the Galaxy, dimana Quill mencari Gamora 2014. Rogers mengembalikan Batu Infinity dan Mjolnir ke waktunya masing-masing dan menetap di masa lalu untuk hidup bersama Peggy Carter. Di masa kini, Rogers yang sudah tua memberikan perisai dan nama Captain America kepada Sam Wilson.\n",
                "https://youtu.be/TcMBFSGVi1c\n"

        );

        tambahFilm(film1, db);
        idFilm++;


// Data film ke-2
        try {
            tempDate = sdFormat.parse("24/05/2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "Aladdin",
                tempDate,
                storeImageFile(R.drawable.aladdin2),
                "Guy Ritchie",
                "Will Smith, Mena Massoud, Naomi Scott, Marwan Kenzari, Navid Negahban, Nasim Pedrad, dan Billy Magnussen\n",
                "John August",
                "Aladdin, seorang pencuri muda yang baik hati (sering disebut \"tikus jalanan\") yang tinggal di kota Arab, Agrabah, bersama dengan monyet peliharaannya, Abu, menyelamatkan dan berteman dengan Putri Jasmine, yang menyelinap keluar dari istana untuk menjelajah, bosan dengan hidupnya yang terlindung. Sementara itu, wazir agung Jafar berencana untuk menggulingkan ayah Jasmine sebagai Sultan. Dia, bersama dengan sahabat burung kakatua peliharaannya Iago, mencari lampu ajaib yang tersembunyi di Gua Keajaiban yang akan memberinya tiga permintaan. Hanya satu orang yang layak untuk masuk: \"berlian dalam kesulitan\", yang dia putuskan adalah Aladdin. Aladdin ditangkap dan Jafar membujuknya untuk mengambil lampu. Di dalam gua, Aladdin menemukan karpet ajaib dan mendapatkan lampu. Dia memberikannya kepada Jafar, yang melipat gandakan salibnya dan melemparkannya kembali ke gua, meskipun Abu mencuri lampu kembali.\n" +
                        "Terperangkap di dalam gua, Aladdin menggosok lampu, tanpa sadar memanggil Genie, makhluk maha kuasa yang tinggal di dalamnya. Genie menjelaskan bahwa ia memiliki kekuatan untuk mengabulkan tiga permintaan Aladdin. Aladdin menipu Genie agar membebaskan mereka dari gua tanpa menggunakan keinginan. Setelah mereka keluar dari gua, Aladdin menggunakan keinginan resmi pertamanya untuk menjadi seorang pangeran untuk mengesankan Jasmine, dan berjanji untuk menggunakan keinginan ketiga untuk membebaskan Jin dari perbudakan.\n" +
                        "Aladdin memasuki Agrabah sebagai Pangeran Ali dari Ababwa, tiba di sebuah tontonan yang luar biasa — termasuk Abu, yang telah diubah oleh Genie menjadi gajah. Tapi Jasmine tidak terkesan dengan presentasi pertamanya, termasuk bermacam-macam hadiah dan selai. Dua ikatan kemudian ketika dia membawanya pada karpet ajaib untuk menunjukkan padanya dunia yang ingin dia lihat sementara Genie pergi dengan Dalia, pelayan perempuan Jasmine. Ketika Jasmine menipu Aladdin untuk mengungkapkan identitas aslinya, dia meyakinkannya bahwa dia sebenarnya seorang pangeran dan hanya berpakaian seperti petani untuk bertemu warga Agrabah sebelumnya. Jafar menemukan identitas Aladdin dan untuk menguji teorinya, melemparkan Aladdin ke laut. Aladdin kehilangan kesadaran dan terbangun karena telah diselamatkan oleh jin, dengan mengorbankan harapan keduanya. Mereka kemudian mengungkap Jafar, yang ditangkap dan dipenjara di ruang bawah tanah. Setelah Sultan menawarkan posisi Aladdin sebagai ahli waris, Aladdin, takut dia akan kehilangan Jasmine jika kebenaran terungkap, mengatakan dia membutuhkan Genie bersamanya sekarang dan menolak untuk membebaskannya. Genie memberi tahu Aladdin bahwa dia tidak jujur pada dirinya sendiri.\n" +
                        "Iago mengambil salah satu kunci penjaga dan dia membebaskan Jafar. Jafar mencuri lampu secara diam-diam dari Aladdin dan menjadi tuan baru Genie. Dia menggunakan dua keinginan pertamanya untuk menjadi Sultan dan kemudian menjadi penyihir terkuat di dunia, menjebak para penjaga dan harimau peliharaan Jasmine, Rajah. Dia kemudian mengekspos kebenaran Aladin untuk Jasmine dan mengasingkannya dan Abu ke tanah beku. Dia mengancam akan membunuh ayah Jasmine dan Dalia kecuali dia setuju untuk menikah dengannya. Pada upacara pernikahan, Aladdin dan Abu kembali, setelah diselamatkan oleh karpet ajaib dan Jasmine mencuri lampu. Marah, Jafar mengubah Iago menjadi roc untuk mengejar dan mengalahkan mereka.\n" +
                        "Aladdin berhenti dengan mengejek Jafar karena menjadi yang kedua setelah Genie dalam hal kekuatan mentah, dengan demikian menipu dia untuk menggunakan keinginan terakhirnya untuk menjadi makhluk paling kuat di alam semesta. Karena area abu-abu dalam keinginan itu, Genie bebas untuk menafsirkannya sesuai keinginannya dan mengubah Jafar menjadi seorang jin sendiri. Dirantai ke lampu tanpa seorang pemilik, Jafar terjebak di dalam, menyeret Iago ke dalam bersamanya. Genie melemparkan lampu Jafar ke Gua Keajaiban dan Aladdin menepati janjinya, menggunakan keinginan terakhirnya untuk membebaskan Genie dan menjadikannya manusia. Sultan menyatakan bahwa Jasmine akan menjadi penguasa berikutnya dan mengatakan kepadanya bahwa Aladdin adalah orang yang baik, menguraikan betapa layaknya dia dan mengatakan padanya sebagai Sultan, dia dapat membatalkan hukum yang mengharuskannya menikahi seorang pangeran. Jasmine mengikuti Aladdin ke luar istana di mana dia \"memerintahkannya\" untuk datang dan menghadap Sultan, dia dan Aladdin kemudian berbagi ciuman dengan penuh gairah. Genie menikahi Dalia dan pergi untuk menjelajahi dunia dan memulai keluarga dengannya. Aladdin dan Jasmine menikah dan memulai hidup baru.",
                "https://youtu.be/foyufD52aog\n"
        );


        tambahFilm(film2, db);
        idFilm++;


        // Data film ke-3
        try {
            tempDate = sdFormat.parse("26/07/2013");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "The Conjuring",
                tempDate,
                storeImageFile(R.drawable.conjuring3),
                "James Wan",
                "Vera Farmiga, Patrick Wilson, Lili Taylor, Ron Livingston, Shanley Caswell, Hayley McFarland, Joey King,  Mackenzie Foy,  Kyla Deaver, Shannon Kook, John Brotherton, Sterling Jerins , Marion Guyot , Steve Coulter , Joseph Bishara\n",
                "Chad Hayes dan Carey Hayes",
                "Pada tahun 1971, Carolyn dan Roger Perron pindah ke sebuah rumah pertanian tua di Harrisville, Rhode Island, bersama kelima putri mereka. Selama hari pertama, kepindahan keluarga ini berjalan lancar, meskipun anjing mereka, Sadie, menolak memasuki rumah dan salah satu anak perempuan mereka menemukan sebuah pintu masuk ke ruang bawah tanah.\n" +
                        "Keesokan paginya, Carolyn bangun dengan memar misterius dan menemukan Sadie tergeletak mati di luar rumah. Selama beberapa hari berikutnya, berbagai peristiwa misterius terjadi. Peristiwa ini memuncak pada suatu malam saat Roger berada di Florida, Carolyn terkunci di ruang bawah tanah dan sesosok makhluk gaib yang terlihat seperti seorang wanita tua menyerang salah satu putrinya.\n" +
                        "Carolyn menghubungi investigator paranormal bernama Ed dan Lorraine Warren untuk meminta bantuan. Pasangan Warren mulai melakukan penyelidikan dan menyimpulkan bahwa rumah tersebut mungkin membutuhkan ritual pengusiran setan, tetapi ritual tersebut tidak bisa dilakukan sebelum mendapat izin dari Gereja Katolik.\n" +
                        "Saat menyelidiki tentang sejarah rumah tersebut, Ed dan Lorraine mengetahui bahwa rumah tersebut dulunya dimiliki oleh seseorang yang diduga penyihir bernama Bathsheba. Bathsheba mengorbankan anak-anaknya kepada setan, kemudian bunuh diri dan mengutuk semua orang yang mencoba mengambil tanahnya. Tanahnya ini dulunya seluas 200 hektare lebih, tetapi telah dibagi-bagi. Mereka berdua juga mengetahui bahwa banyak laporan pembunuhan dan bunuh diri yang terjadi di rumah-rumah yang dibangun di atas tanah yang dulunya dimiliki oleh Bathsheba.\n" +
                        "Ed dan Lorraine kembali ke rumah dan berupaya untuk mengumpulkan bukti agar mendapat izin dari Gereja Katolik untuk melakukan ritual pengusiran setan. Salah satu putri Carolyn, Cindy, berjalan dalam tidurnya dan menemukan sebuah pintu rahasia di belakang lemari. Lorraine memasuki pintu tersebut dan jatuh ke ruang bawah tanah. Di sana, ia menyaksikan arwah orang-orang yang pernah dirasuki Bathsheba. Semuanya adalah para ibu yang dirasuki oleh Bathsheba untuk membunuh anak-anak mereka. Sementara itu, putri Perron yang lainnya diserang dan diseret sepanjang lantai oleh suatu kekuatan tak terlihat\n" +
                        "Lorraine dan Ed berhasil mengumpulkan bukti bagi Gereja untuk melakukan ritual pengusiran setan, sedangkan keluarga Perron berlindung di sebuah hotel. Sementara itu, putri Ed dan Lorraine diserang di rumah mereka oleh arwah yang berasal dari rumah Perron.\n" +
                        "Carolyn, yang telah dirasuki oleh Bathsheba, membawa dua putrinya, Christine dan April, kembali ke rumah. Ed, Lorraine, dua asisten mereka, dan Roger bergegas menyusulnya dan menemukan Carolyn tengah berupaya untuk menusuk Christine. Setelah melumpuhkan Carolyn, Ed memutuskan untuk melakukan ritual pengusiran setan sendirian, tetapi Carolyn berhasil lolos dan mencoba untuk membunuh April. Lorraine berhasil mengalihkan perhatian Carolyn dengan cara mengingatkannya mengenai kenangan khususnya bersama keluarganya, sehingga Ed berhasil menyelesaikan ritual pengusiran setan dan menyelamatkan Carolyn beserta putrinya.",
                "https://youtu.be/k10ETZ41q5o"
        );


        tambahFilm(film3, db);
        idFilm++;


        // Data film ke-4
        try {
            tempDate = sdFormat.parse("19/12/2019 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "Imperfect : Karir, Cinta & Timbangan",
                tempDate,
                storeImageFile(R.drawable.imperfect4),
                "Ernest Prakasa",
                "Jessica Mila, Reza Rahadian, Yasmin Napper, Dion Wiyoko Karina Suwandi, Kiki Narendra, Shareefa Daanish, Dewi Irawan, Ernest Prakasa, Clara Bernadeth, Boy William, Karina Nadila, Devina Aureel, Kiky Saputri, Zsazsa Utari, Aci Resti, Neneng Wulandari, Uus, Diah, Permatasari, Wanda Hamidah, Olga Lydia, Asri Welas, Tutie Kirana, Cathy Sharon\n",
                "Ernest Prakasa, Meira Anastasia\n",
                "Rara adalah anak perempuan yang memiliki hobi makan dalam jumlah besar, sehingga ia menjadi wanita bertubuh gemuk, berbanding terbalik dengan adiknya yang bertubuh kurus. Pada masa kecil, ayahnya tewas dalam kecelakaan di Tol Jagorawi. Setelah itu, rumahnya dijual ibunya dan keluarganya pindah ke rumah baru. Ketika besar, Rara bekerja di kantor yang dipimpin Kelvin. Rara sempat menjadi guru sukarela bagi anak-anak pemulung. Di Malathi, perusahaan tempat ia bekerja, ia mendapatkan perilaku diskriminatif terkait dirinya yang bertubuh gemuk, termasuk Marsha dan dua kawannya, kecuali sahabatnya. Pada suatu hari, Sheila mengumumkan pengunduran diri. Rara diisi ke jabatan yang ditinggalkan. Perusahaan itu mengalami masalah keuangan, jadi Kelvin membutuhkan pengganti yang bisa mengatasi masalah itu. Ia diminta menggunakan waktu selama sebulan untuk merampingkan tubuhnya. Walaupun terpaksa, ia menuruti permintaan atasannya. Ia melanjutkan sejumlah langkah diet hingga berjaya mengurangi beberapa puluh kilogram. Namun, perilakunya mulai berubah seiring beran tubuhnya. Ia akhirnya diterima dalam jaringan perkawanan Marsha, tetapi harus mengorbankan hubungan dengan kawan lamanya. Ia juga lebih memilih menggunakan taksi alih-alih motor, wapau akhirnya terlambat mengajari anak-anak pemulung.\n" +
                        "Ibu Kelvin mendatangi perusahaan Kelvin dan menyebut perusahaan tersebut masih belum berhasil mengatasi keadaan. Kelvin menyebut kinerja perusahaan malah semakin menurun, terdengar Marsha. Marsha berpura-pura merayakan ulang tahun Rara dengan mengajaknya minum anggur. Rara tertidur, sampai hampir melupakan perayaan ulang tahun oleh anak-anak panti. Di sana, anak-anak panti tertidur sedemikian lama menunggu Rara.\n" +
                        "Dalam rapat, Kelvin menyalahkan Rara karena kinerja buruk, lalu Rara jatuh pingsan karena kekurangan karbohidrat. Ketika ia pulih, Dika memarahinya karena perilaku Rara sudah berubah total. Ketika pulang, Dika masih mendapati lelaki berambut merah masih mengganggu wanita berjilbab, kemudian masin terlibat perkelahian, sehingga dirinya terluka dan kameranya rusak. Ibunya menasihatinya dengan menyebut Rara hanya berusaha mencari jati dirinya yang sebenarnya. Di rumah Rara, Rara dan Lulu berkelahi karena Rara mengira dirinya kurang mendapat kasih sayang ibunya, ibunya berusaha menenangkan dirinya. Ibunya mengorbankan kariernya sebagai model hanya agar melahirkannya dalam bentuk caesar, memperlihatkan bekas operasi vertikalnya. Mereka akhirnya berdamaian.\n" +
                        "Rara mendapat kiriman dari Dika yaitu foto dirinya yang mengajar, mengisyaratkan permintaan maaf Reza yang sempat terlibat permasalahan dengannya. Perusahaan itu akhirnya kembali untung dan mengadakan pesta syukuran. Rara akhirnya bahagia karena mendapat perhatian dari semua orang yang hadir di pesta.\n",
                "https://youtu.be/f4Sc26vQHcU"
        );

        tambahFilm(film4, db);
        idFilm++;


        // Data film ke-5
        try {
            tempDate = sdFormat.parse("28/04/2016");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film5 = new Film(
                idFilm,
                "Ada Apa dengan Cinta 2",
                tempDate,
                storeImageFile(R.drawable.aadc5),
                "Riri Riza",
                "Dian Sastrowardoyo, Nicholas Saputra, Titi Kamal, Sissy Priscillia, Adinia Wirasti, Dennis Adhiswara, Ario Bayu, Christian Sugiono, Sarita Thaib, Dimi Cindyastira dan Chase Kuertz\n",
                "Riri Riza, Prima Rusdi",
                "Cinta (Dian Sastrowardoyo) yang sekarang telah menjadi pemilik sebuah Kafe Seni Pop Mini di Jakarta. Dia dan geng SMA menghibur Karmen (Adinia Wirasti), yang baru pulih dari kecanduan narkoba. Milly (Sissy Priscillia) menikah dengan mantan teman sekolahnya, Mamet (Dennis Adhiswara) dan saat ini tengah mengandung anak pertama mereka, sementara Maura (Titi Kamal) menikah dengan Chris (Christian Sugiono) dengan 4 anak. Cinta mengumumkan bahwa ia akan pergi berlibur ke Yogyakarta dan bahwa dia bertunangan dengan pacarnya Trian (Ario Bayu). Sementara itu, di New York City, Rangga (Nicholas Saputra) adalah co-owner sebuah kedai kopi. Saat saudara tirinya, Sukma (Dimi Cindyastira), berkunjung untuk meminta dia untuk mengunjungi ibunya di Jogjakarta, tetapi dia menolak. Dia akhirnya berubah pikiran dan memutuskan untuk pergi ke sana setelah rekan pemilik dan temannya, Roberto (Chase Kuertz) meyakinkan dia bahwa dia harus pulang ke Indonesia untuk menyelesaikan sesuatu yang perlu untuk diselesaikan.\n" +
                        "Sebelum Cinta dan sahabat-sahabatnya pergi, mereka mengunjungi Makam Alya (Ladya Cheryl), yang dijelaskan nanti dalam film dia meninggal dalam kecelakaan pada tahun 2010. Rangga tiba di Jakarta untuk menemukan bahwa Cinta tidak lagi tinggal di rumahnya yang dulu, jadi dia pergi langsung ke Jogja. Di Jogja, saat berlibur, Karmen dan Milly melihat Rangga di jalan, dan mereka memberitahu Cinta tentang hal itu. Cinta tidak ingin melihat Rangga pada awalnya, tetapi setelah bertengkar dengan Karmen, dia memutuskan untuk melakukannya. Hal ini menunjukkan bahwa mereka putus pada tahun 2006. Cinta mengungkapkan kemarahannya atas dirinya untuk hanya memotong hal off dengan tanpa penjelasan dan dia setuju untuk bertemu dengannya karena teman-temannya. Rangga menjelaskan bahwa ia putus dengan Cinta karena dengan itu kemudian berjuang untuk hidup selama tahun-tahun, Rangga pikir ia tidak bisa membuat Cinta bahagia. Mereka memutuskan untuk mengakhiri hal-hal dalam hal ramah. Cinta berakhir menghabiskan sepanjang hari dengan Rangga, melupakan jadwal liburannya dengan teman-temannya. Sebelum berpisah, Cinta mencium Rangga, untuk shock kedua mantan kekasih. Kemudian Cinta kembali ke Jakarta dan Rangga mengunjungi ibunya.\n" +
                        "Cinta tidak bisa biarkan Rangga keluar dari pikirannya dan sebelum dia kembali ke New York, Rangga mampir di Jakarta dan pergi ke kafe Cinta. Dia mengakui bahwa ia ingin mereka menjadi lebih dari teman-teman lagi tetapi dia menjawab bahwa ciuman itu tidak ada artinya, yang membuat dia marah. Dan, Rangga meninggalkan kafe saat Trian tiba di kafe. Trian, melihat perilaku Cinta berubah setelah perjalanan Jogja dan melihat Rangga sendiri, berhadapan dengan Cinta tentang Rangga, yang mengakibatkan berakhirnya pertunangan. Rangga terbang kembali ke New York sementara Cinta mengalami sedikit kecelakaan, tetapi tidak mendapat cedera.\n" +
                        "Setahun kemudian, Rangga dan Roberto memberikan karyawan mereka Donna (Lei-Lei Bavoil) kenaikan gaji, dan dengan rasa terima kasih ia memeluk Rangga. Pada saat yang sama, Cinta memasuki kafe dan kemudian melarikan diri. Rangga menjelaskan situasi dan Cinta tertawa. Mereka mengungkapkan cinta mereka satu sama lain, dan berbagi ciuman penuh gairah di Central Park yang bersalju, bersatu pada akhirnya. Sebelum berakhir, Rangga dan Cinta yang tampaknya memegang bayi mereka, sampai Mamet datang dan meminta dia dan bayi Milly nya kembali.\n",
                "https://youtu.be/3c_McS4_2A8"
        );

        tambahFilm(film5, db);
        idFilm++;


    }
}

