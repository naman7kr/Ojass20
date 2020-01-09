package ojass20.nitjsr.in.ojass.Utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ojass20.nitjsr.in.ojass.R;

public class Constants {
    public static final String FIREBASE_REF_POSTERIMAGES = "PosterImages";
    public static final String FIREBASE_REF_IMG_SRC = "img_url";

    public static final int[] eventImg = new int[]{
            R.mipmap.rise_of_machines,
            R.mipmap.code_genesis,
            R.mipmap.circuit_house,
            R.mipmap.silicon_valley,
            R.mipmap.arthashastra,
            R.mipmap.akriti,
            R.mipmap.dxm,
            R.mipmap.produs,
            R.mipmap.paraphernalia,
            R.mipmap.neo_drishti,
            R.mipmap.avartan,
            R.mipmap.armagedon,
            R.mipmap.prayas,
            R.mipmap.no_ground_zone,
            R.mipmap.nscet,
            R.mipmap.csgolive,
            R.mipmap.exposicion,
            R.mipmap.school_events,
            R.mipmap.school_events
    };
    public static final ArrayList<String> eventNames = new ArrayList<>();
    public static final HashMap<String, ArrayList<String>> SubEventsMap = new HashMap<>();

    //    public static final String eventNames[] = new String[]{
//            "Rise of Machines",
//            "Vishwa Code Genesis",
//            "Circuit House",
//            "Silicon Valley",
//            "Arthashastra",
//            "Aakriti",
//            "Deus-X-Machina",
//            "Produs",
//            "Paraphernalia",
//            "Neo Drishti",
//            "Aavartan",
//            "Armageddon",
//            "Prayas",
//            "No Ground Zone",
//            "NSCET",
//            "Live CS",
//            "Exposicion",
//            "School Events",
//            "Checkered Flag"
//    };
    public static HashMap<Integer, ArrayList<String>> SubEventsList = new HashMap<>();
//        public static final String[][] SubEventsList = new String[][];
//    {
//            {"Mech-Trivia", "GURUTWA", "iANSYST", "BOX-CIPHER", "CORPORATE BYTES", "Junkyard Wars", "Prakshepan"},
//            {"Codemania", "Hack-De-Science", "Code-o-Soccer", "Comp Geeks", "Sudo-Code", "Imitation Game", "Bamboozle"},
//            {"High Voltage Concepts", "Elixir of Electricity", "ELECTROSPECTION", "Electro Scribble", "MAT-SIM", "Electro-Q", "Who Am I?"},
//            {"TUKVILLA", "Jigyasa", "CODESENSE", "NETKRAFT", "EMBETRIX", "Electonics Frenzy", "Tech Today"},
//            {"Toddler to Tycoon(TTT)", "Prabandh Yojana", "Let's Start Up", "Bizzathlon", "Corporate Roadies"},
//            {"Acumen", "Sanrachna", "Exempler", "Pipe-o-Mania", "Metropolis", "DEXTEROUS"},
//            {"360 Mania", "Tachyon", "Battleship", "Kurukshetra", "MAC FIFA", "Shapeshifter", "BOT-A MAZE", "Hurdles Hunter"},
//            {"Industrial Tycoon", "DronaGyan", "Utpreksh", "Crack the Case", "M&I Quiz", "DronGyan"},
//            {"Light, Camera, Ojass!", "Mad-Ad", "Lens View", "Film Chaupaal", "Jumbo Films"},
//            {"Codiyapa", "Game of Troves", "Tame the Python", "Code Relay", "Capture the Flag"},
//            {"Spectra", "Agnikund", "Metal Trivia", "Innovision", "Knock out"},
//            {"FIFA 18", "Counter Strike- Global Offensive", "NFS Most Wanted", "LUDO King", "PUBG Mobile", "POKEMON GO"},
//            {"Pratibimb", "PARAKRAM"},
//            {"Touch Down the plane", "MICAV"},
//            {"NSCET"},
//            {"LiveCS"},
//            {"Exposicion"},
//            {"Teenpreneur", "The Wiz Craft"},
//            {"DISASSEMBLE", "SOLIDWORKS", "GO ELECTRIC"}

    //    }
    public static final String FIREBASE_REF_NOTIF = "Notifications";

    public static void updateSubEventsArray() {
        SubEventsList.clear();
        int j = 0;
        for (Map.Entry<String, ArrayList<String>> entry : SubEventsMap.entrySet()) {
            Log.e("cons", entry.getKey());
            for (j = 0; j < eventNames.size(); j++) {
                if (eventNames.get(j).equalsIgnoreCase(entry.getKey()))
                    break;
            }
            for (int i = 0; i < entry.getValue().size(); i++)
                Log.e("cons", entry.getValue().get(i));
            Log.e("cons", " ");
            SubEventsList.put(j, entry.getValue());
        }
    }
}
