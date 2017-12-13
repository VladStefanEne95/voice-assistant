package voice.asistant;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Logger;

public class VoiceLauncher {
    public static void getTime(String city) {
        
    }
    public static void meteo () {
        
        try {
        // Create a URL for the desired page
        URL url = new URL("https://www.accuweather.com/ro/ro/bucharest/287430/daily-weather-forecast/287430");       

        // Read all the text returned by the server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        while ((str = in.readLine()) != null) {
            str = in.readLine().toString();
            if(str.contains("small-temp")) {
                str = str.replace("<span class=\"small-temp\">/", "");
                str = str.replace("&deg;C</span>", "");
                str = str.replace(" ", "");
                System.out.println("Temperatura minima este " + str);
                break;
            // str is one line of text; readLine() strips the newline character(s)
            }
        }
        in.close();
    } catch (MalformedURLException e) {
    } catch (IOException e) {
    }
    }
    
        public static void tren () {
        
        try {
        // Create a URL for the desired page
        URL url = new URL("http://mersultrenurilorcfr.ro/imtif/rute.aspx?key=1UUauKIuq0VCUjGXqBdWwJfSHYsvwHeax06005OnWjsgKcdkLWRCYWW7lsYbRLP0svNjZksfOBiTplZp665o54h6LhBALyyQn+giuK6B3LEyOiiXqeGJIRDKeLqujqZIOfvGFb4n2LUUMnyqfZZ4Osm3dtS4eVbxURicb+OPOu6iAzrsu0UZEQRgcibpQZYm1Qk+Pgo12aJvTPslBoP0yre11nfa7AAK31/vJqFi7/JXT65088G3Lf34509bYWkARhuZUwnnp/DoSTGaVuxYHHfA9KWDyj7jhykWL8XD9OGtr2vgIvw18VRNI1Kxu082&lng=ro");       

        // Read all the text returned by the server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        while ((str = in.readLine()) != null) {
            str = in.readLine();
            if(str.contains("ctl00_ContentPlaceHolder1_lblret")){
                String end = "Severin</td><td>";
                int nr = str.indexOf(end);
                str = str.substring(nr + end.length(), nr + end.length() + 5);
                System.out.println("Trenul pleaca la ora " + str);
                break;   
            }
           
            // str is one line of text; readLine() strips the newline character(s)
            
        }
        in.close();
    } catch (MalformedURLException e) {
    } catch (IOException e) {
    }
    }
    
    public static void main(String[] args) throws IOException {
       
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("dictionar.dic");
        configuration.setLanguageModelPath("lang-model.lm");

        LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);

        
        
        recognize.startRecognition(true);
        
        System.out.println("Starting recognition...");
        
        SpeechResult result;
        int counter = 0;

        while ((result = recognize.getResult()) != null) {
            
            String command = result.getHypothesis();
            String work = "";
            
            if(command.length() != 0)
                System.out.println(command);
            
            if (command.equalsIgnoreCase("open browser")) {
                work = "start chrome.exe ";
                counter = 1;
            } else if (command.equalsIgnoreCase("close browser")) {
                work = "taskkill /IM chrome.exe >nul";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("open mail")) {
                work = "start https://mail.yahoo.com/";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("open facebook")) {
                work = "start https://www.facebook.com/";
                counter = 1;
            }
            else if (command.toLowerCase().contains("open youtube")) {
                if (command.toLowerCase().contains("techno")) {
                    work = "start https://www.youtube.com/watch?v=GVp-UOEur6Y&list=RD6UOxEeQ8W24&index=2";
                } else if(command.toLowerCase().contains("house")) {
                    work = "start https://www.youtube.com/watch?v=qke-jOUqSXU&list=RDqke-jOUqSXU";
                } else if (command.toLowerCase().contains("opera")) {
                    work = "start https://www.youtube.com/watch?v=7qHZkkgowdY&list=RD7qHZkkgowdY&t=3";
                } else {
                    work = "start https://www.youtube.com/";
                }
                counter = 1;
            }
            else if (command.toLowerCase().contains("show time")) {
                SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
                if (command.toLowerCase().contains("london")) {
                    f.setTimeZone(TimeZone.getTimeZone("Europe/London"));
                    System.out.println(f.format(GregorianCalendar.getInstance().getTime()));
                } else if (command.toLowerCase().contains("singapore")) {
                    f.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
                    System.out.println(f.format(GregorianCalendar.getInstance().getTime()));
                } else if (command.toLowerCase().contains("rome")) {
                    f.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
                    System.out.println(f.format(GregorianCalendar.getInstance().getTime()));
                } else if (command.toLowerCase().contains("berlin")) {
                    f.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                    System.out.println(f.format(GregorianCalendar.getInstance().getTime()));
                } else if (command.toLowerCase().contains("dubai")) {
                    f.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
                    System.out.println(f.format(GregorianCalendar.getInstance().getTime()));
                } else {
                    System.out.println("Local time is: ");
                    work = "time/T";
                }
                
                counter = 1;
            } 
            else if (command.toLowerCase().contains("open reddit")) {
                if (command.toLowerCase().contains("funny")) {
                    work = "start https://www.reddit.com/r/funny/";
                } else if (command.toLowerCase().contains("random")) {
                    work = "start https://www.reddit.com/r/random/";
                } else if (command.toLowerCase().toLowerCase().contains("popular")) {
                    work = "start https://www.reddit.com/r/popular/";
                } else {
                    work = "start https://www.reddit.com/";
                }
                counter = 1;
            }
            else if (command.equalsIgnoreCase("show date")) {
                work = "echo %Date%";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("get mac")) {
                work = "getmac";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("get connected user")) {
                work = "whoami";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("open media player")) {
                work = "start wmplayer.exe";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("close media player")) {
                work = "taskkill /IM wmplayer.exe";
                counter = 1;
            }
            else if (command.equalsIgnoreCase("display meteo")) {
                meteo();
                counter = 1;
            }
            else if (command.equalsIgnoreCase("next train")) {
                tren();
                counter = 1;
            } else if (command.equalsIgnoreCase("open notepad")) {
                work = "start notepad++.exe";
                counter = 1;
            } else if (command.equalsIgnoreCase("close notepad")) {
                work = "taskkill /IM notepad++.exe";
                counter = 1;
            } else if (command.equalsIgnoreCase("open winrar")) {
                work = "start winrar.exe";
                counter = 1;
            } else if (command.equalsIgnoreCase("close winrar")) {
                work = "taskkill /IM winrar.exe";
                counter = 1;
            }
            
            if(counter == 1) {
                
                ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", work);
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while (true) {
                    line = r.readLine();
                    if (line == null) { break; }
                    System.out.println(line);
                }
            counter = 0;         
        }
      }
    }
 
}
