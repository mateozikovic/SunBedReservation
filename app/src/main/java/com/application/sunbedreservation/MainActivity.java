package com.application.sunbedreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.application.sunbedreservation.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    DatabaseReference newsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance();
        newsRef = database.getReference("News");

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.reservations:
                    replaceFragment(new ReservationsFragment());
                    break;
                case R.id.news:
                    replaceFragment(new NewsFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });

         // this is used just to fill the database with dummy data

//         StoreDataInFirebase fillDB = new StoreDataInFirebase();
//        fillDB.writeNewBeach("Zlatne Stijene", "Lijepa plaza", "pic.jpg",
//                "44.84640377977642", "13.833914353971652", "20");
//        fillDB.writeNewBeach("Pical Beach", "Plaza u blizini hotela Parentino", "pic.jpg",
//                "45.23498102948223", "13.596537451227437", "15");
//        fillDB.writeNewBeach("Materada Beach", "Plaza u blizini hotela Materada", "pic.jpg",
//                "45.24926887641833", "13.591844956120799", "25");

        /*
        * TODO create a sub-document in each beach for beach prices and fill it with dummy data
        * */
        DatabaseReference beachesRef = database.getReference("Beaches");
        DatabaseReference newsRef = database.getReference("News");
        addDummyDataToFirebase();

        beachesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot beachSnapshot: snapshot.getChildren()){
                    Log.i("beachtag", String.valueOf(beachSnapshot.getKey()));
                    // get the document ID
                    String documentID = beachSnapshot.getKey();

                    // set the prices for all beaches
                    SunbedPrices sunbedPrices = new SunbedPrices();
                    sunbedPrices.setRow1June(20);
                    sunbedPrices.setRow2June(18);
                    sunbedPrices.setRow1July(25);
                    sunbedPrices.setRow2July(23);
                    sunbedPrices.setRow1August(27);
                    sunbedPrices.setRow2August(25);
                    sunbedPrices.setRow1September(18);
                    sunbedPrices.setRow2September(16);

                    beachesRef.child(documentID).child("BeachPrices").setValue(sunbedPrices);


//                    Sunbed[] sunbeds = {
//                            new Sunbed("sunbed1", 1),
//                            new Sunbed("sunbed2", 1),
//                            new Sunbed("sunbed3", 1),
//                            new Sunbed("sunbed4", 1),
//                            new Sunbed("sunbed5", 2),
//                            new Sunbed("sunbed6", 2),
//                            new Sunbed("sunbed7", 2),
//                            new Sunbed("sunbed8", 2)
//                    };
//
//                    for (Sunbed sunbed : sunbeds) {
//                        String beachId = beachesRef.child("beaches").push().getKey();
//                        beachesRef.child(documentID).child("sunbeds").child(sunbed.getId()).setValue(sunbed);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("BeachPrice Error", String.valueOf(error));
            }
        });
    }

    // Step 1: Generate Dummy Data
    private List<News> generateDummyData() {
        List<News> dummyDataList = new ArrayList<>();

        // Create and add dummy data objects
        long currentTimeMillis = System.currentTimeMillis();
        News news1 = new News("Skriveni dragulji Istre: Otkrivanje čari manje poznatih ljepota", "Dobrodošli u Istru, očaravajuću regiju koja oduzima dah svojom bogatom poviješću, nevjerojatnom kulturom i prekrasnom prirodom. Ovo živopisno poluotok nalazi se na sjeverozapadu Hrvatske, tik uz Jadransko more. Istra oduševljava posjetitelje svojom raznolikošću - od slikovitih srednjovjekovnih gradova poput Rovinja, Poreča i Motovuna do netaknutih prirodnih ljepota unutar Nacionalnog parka Brijuni i Parka prirode Učka.\"\n" +
                "\n" +
                "\"Jedan od glavnih aduta Istre je njezina gastronomska scena koja vas neće ostaviti ravnodušnima. Uživajte u autentičnim istarskim jelima poput fuži s tartufima, maneštre, pršuta i fantastičnih maslinovih ulja. Ovo područje je poznato po bogatstvu svježih plodova mora, što će zadovoljiti i najzahtjevnija nepca.\"\n" +
                "\n" +
                "\"Kroz povijest, Istra je bila žarište raznih kultura i civilizacija, što je ostavilo trag na njezinoj arhitekturi i umjetnosti. Očarajte se mnoštvom spomenika, crkava i muzeja koji svjedoče o bogatoj prošlosti ovog područja. Posjetite pulsirajuće gradove i mirna sela, svaki s pričom za ispričati.\"\n" +
                "\n" +
                "\"Kada je riječ o aktivnostima, Istra ima nešto za svakoga. Uživajte u raznolikim sportskim aktivnostima na moru poput ronjenja, jedrenja na dasci i jedrenja. Za ljubitelje prirode, ovdje su prekrasni biciklistički i planinarski putevi koji vas vode kroz netaknutu prirodu.\"\n" +
                "\n" +
                "\"Ne zaboravite posjetiti neke od najboljih plaža na Jadranskoj obali. Od skrivenih uvala do dužih šljunčanih plaža, Istra nudi mnoštvo mogućnosti za opuštanje i uživanje na suncu.\"\n" +
                "\n" +
                "\"Istra vas poziva da doživite njezinu neograničenu ljepotu i gostoljubivost. Bilo da ste avanturistički putnik, kulturni istraživač ili jednostavno tražite miran bijeg od svakodnevice, Istra će vas zasigurno osvojiti svojom raznolikošću i šarmom.\"", "Istra, Hrvatska", "https://www.bretonne-en-croatie.com/data/images/image/newbig/9505/apartman.jpeg", currentTimeMillis);
        dummyDataList.add(news1);

        News news2 = new News("Hrvatska - Biser Jadrana", "\\\"Dobrodošli u Hrvatsku, čarobni biser Jadrana! Smještena na raskrižju Srednje i Jugoistočne Europe, Hrvatska mami putnike svojom predivnom obalom, netaknutim plažama i kristalno čistim tirkiznim vodama. Od srednjovjekovne čarolije starog grada Dubrovnika do povijesnih čuda Dioklecijanove palače u Splitu, Hrvatska nudi zavodljivu mješavinu bogate povijesti i modernog sjaja.\\\"\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"\\\"Istražite zadivljujući Nacionalni park Plitvička jezera, UNESCO-ova svjetska baština, gdje niz kaskadnih jezera i vodopada stvara čarobni svijet. Proputujte šarmantni istarski poluotok poznat po slikovitim brdskim gradovima i izvrsnim trufelnim jelima. Osvojite se prirodnim ljepotama dalmatinskih otoka, gdje vas čekaju idilične plaže i drevni ostaci.\\\"\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"\\\"Hrvatska nije samo o obali; krenite unutrašnjosti prema čarobnom glavnom gradu Zagrebu, gdje oživljavaju povijest, kultura i umjetnost. Kušajte lokalnu kuhinju u tradicionalnim konobama, uživajući u jelima kao što su 'peka', 'ćevapi' i 'pasticada'. Hrvatska kulinarska čarolija sigurno će vas oduševiti.\\\"\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"\\\"Bilo da ste avanturist u potrazi za uzbuđenjima u aktivnostima na otvorenom poput jedrenja, planinarenja ili kajakaštva, ili ljubitelj kulture koji istražuje drevno nasljeđe zemlje, Hrvatska nudi nešto za svakoga. Doživite toplu gostoljubivost hrvatskog naroda i uronite u živahnu atmosferu lokalnih festivala i događanja.\\\"\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"\\\"Kako sunce zalazi nad Jadranskim morem, Hrvatska postaje čarobni raj ljepote i romantike. Otkrijte zašto je Hrvatska postala vrhunskom destinacijom za putnike koji traže jedinstvenu kombinaciju prirodnih čuda, kulturne raznolikosti i nezaboravnih iskustava.\\\"", "Croatia", "https://media.cnn.com/api/v1/images/stellar/prod/230526055836-dupe-03-croatia-dalmatia-trogir-zadar-kornati-national-park.jpg?c=original&q=w_1280,c_fill", currentTimeMillis);
        dummyDataList.add(news2);

        return dummyDataList;
    }

    // Step 2: Save Dummy Data to Firebase Realtime Database
    private void addDummyDataToFirebase() {
        List<News> dummyDataList = generateDummyData();

        for (int i = 0; i < dummyDataList.size(); i++) {
            newsRef.child("news" + (i + 1)).setValue(dummyDataList.get(i));
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}