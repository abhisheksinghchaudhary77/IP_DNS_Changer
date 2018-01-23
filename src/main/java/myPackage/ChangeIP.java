package myPackage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class ChangeIP {

    // Method to set Custom IP and DNS
    public static void customIPDNS(String ipAddress, String subnetMask, String gateway, String dns) throws IOException {
        Process changeIPAddr = Runtime.getRuntime().exec("netsh interface ipv4 set address name=Wi-Fi static "+ipAddress+" "+subnetMask+" "+gateway);
        Process changeDNSAddr = Runtime.getRuntime().exec("netsh interface ipv4 set dnsservers name=Wi-Fi source=static address="+dns+" register=primary validate=no");

        System.out.println("Custom IP & DNS set successfully.");
    }

    //Method to set Default IP and DNS
    public static void defaultIPDNS() throws IOException {
        Process changeIPAddr = Runtime.getRuntime().exec("netsh interface ipv4 set address name=Wi-Fi source=dhcp");
        Process changeDNSAddr = Runtime.getRuntime().exec("netsh interface ipv4 set dnsservers name=Wi-Fi source=dhcp");

        System.out.println("Default IP & DNS set successfully.");
    }


    // Fetching Details.............................

    //Get IP Address form user
    public static String getIP() throws IOException {
        Scanner scan=new Scanner(System.in);
        String ipAddress;
        System.out.print("Enter IP (xxx.xxx.xxx.xxx){0<=xxx<=255}: ");
        ipAddress =scan.next();
//        InetAddressValidator validator=new InetAddressValidator();
//        if (!validator.isValidInet4Address(ipAddress)){
//            System.out.println("Error in IP Address. 0-255 are accepted in each octet.");
//            getIP();
//        }
        System.out.println();

        return ipAddress;
    }

    //Get Subnet Mask form user
    public static String getSubnetMask() throws IOException {
        Scanner scan=new Scanner(System.in);
        String subnetMask;
        System.out.print("Enter Subnet Mask (xxx.xxx.xxx.xxx){0<=xxx<=255}: ");
        subnetMask =scan.next();
//        InetAddressValidator validator=new InetAddressValidator();
//        if (!validator.isValidInet4Address(subnetMask)){
//            System.out.println("Error in Subnet Mask. Try 255.255.xxx.xxx");
//            getSubnetMask();
//        }
        System.out.println();

        return subnetMask;
    }

    //Get Default Gateway from user
    public static String getGateway() throws IOException {
        Scanner scan=new Scanner(System.in);
        String gateway;
        System.out.print("Enter Default Gateway (xxx.xxx.xxx.xxx){0<=xxx<=255}: ");
        gateway =scan.next();
//        InetAddressValidator validator=new InetAddressValidator();
//        if (!validator.isValidInet4Address(gateway)){
//            System.out.println("Error in Default Gateway. 0-255 are accepted in each octet.");
//            getGateway();
//        }
        System.out.println();

        return gateway;
    }

    //Get Primary DNS from user
    public static String getDNS() throws IOException {
        Scanner scan=new Scanner(System.in);
        String dns;
        System.out.print("Enter Primary DNS (xxx.xxx.xxx.xxx){0<=xxx<=255}: ");
        dns =scan.next();
//        InetAddressValidator validator=new InetAddressValidator();
//        if (!validator.isValidInet4Address(dns)){
//            System.out.println("Error in IP Address. Try 8.8.8.8 or contact your network service provider.");
//            getDNS();
//        }
        System.out.println();

        return dns;
    }



    //Switch case menu for user
    public static void chooseMe(int choice) throws IOException, JSONException {

        boolean isBool= true;
         while(isBool==true) {
             Scanner scan = new Scanner(System.in);
             switch (choice) {
                 case 1:
                     defaultIPDNS();
                     isBool=false;
                     break;
                 case 2:

                     boolean isbool2=true;
                     while(isbool2==true) {
                         System.out.println("");
                         System.out.println("    Choose an option: ");
                         System.out.println("       1. Select a saved profile.");
                         System.out.println("       2. Enter New IP & DNS.");
                         int choice2 = scan.nextInt();
                         switch (choice2) {
                             case 1:
                                 File file = new File("C:\\MySavedProfiles");
                                 String[] fileList = file.list();
                                 for(String name:fileList){
                                     System.out.println(name);
                                 }

                                 System.out.print("Enter the name of profile (without extension): ");
                                 String chosenFile = scan.next();

                                 File f=new File("C:\\MySavedProfiles\\"+chosenFile+".txt");
                                 if (f.exists()){
                                     InputStream is=new FileInputStream(f);
                                     String jsonText= org.apache.commons.io.IOUtils.toString(is,"UTF-8");
//                                     System.out.println(jsonText.toString());
                                     JSONObject jsonObject=new JSONObject(jsonText);
                                     String ip=(String)jsonObject.get("IP");
                                     String dns=(String)jsonObject.get("DNS");
                                     String gateway=(String)jsonObject.get("Gateway");
                                     String subnet=(String)jsonObject.get("Subnet");

                                     customIPDNS(ip,subnet,gateway,dns);
                                     System.out.println("Profile "+chosenFile+" set successfully!");
                                 }

                                 isbool2 = false;
                                 break;
                             case 2:

                                 String ipAddress = getIP();
                                 String subnetMask = getSubnetMask();
                                 String gateway = getGateway();
                                 String dns = getDNS();
                                 boolean isSave = true;

                                 //SAVING A NETWORK PROFLE

                                 while (isSave == true) {
                                     System.out.print("    Do you want to save this setting? Y/N :  ");
                                     String save = scan.next();

                                     if (save.equals("Y") || save.equals("y")) {

                                         System.out.print("     Enter the Profile Name: ");
                                         String profileName = scan.next();

//                                         IPv4 ipDetails=new IPv4();

                                         File dir=new File("C:\\MySavedProfiles");
                                         if (!dir.exists()){

                                                 dir.mkdir();

                                         }
                                         FileWriter printWriter = new FileWriter("C:\\MySavedProfiles\\"+profileName+".txt");
//                                          printWriter.println("IP:" + ipAddress);
//                                         ipDetails.ip=ipAddress;
//                                         printWriter.println("Subnet:" + subnetMask);
//                                         ipDetails.subnetMask=subnetMask;
//                                         printWriter.println("Gateway:" + gateway);
//                                         ipDetails.gateway=gateway;
//                                         printWriter.println("DNS:" + dns);
//                                         ipDetails.dns=dns;

                                         JSONObject jsonObject=new JSONObject();
                                         jsonObject.put("IP",ipAddress);
                                         jsonObject.put("Subnet",subnetMask);
                                         jsonObject.put("Gateway",gateway);
                                         jsonObject.put("DNS",dns);
                                         printWriter.write(jsonObject.toString());
                                         printWriter.flush();
                                         printWriter.close();

                                         isSave = false;

                                     } else if (save.equals("N") || save.equals("n")) {

                                         isSave = false;

                                     } else {
                                         System.out.println("       Wrong Input, try again!");

                                     }
                                 }


                                 customIPDNS(ipAddress, subnetMask, gateway, dns);

                                 isbool2 = false;
                                 break;

                             default:
                                 System.out.println("    Wrong Input, try again!");

                         }
                     }
                     isBool=false;
                     break;

                 default:
                     System.out.println("Wrong Input, try again!");

             }
         }
    }

    //Main Function
    public static void main(String[] args) throws IOException, JSONException {



        System.out.println("_________________IP Buddy_________________");
        System.out.println();
        System.out.println("Choose from the following menu:");
        System.out.println("   1. Default IP & DNS");
        System.out.println("   2. Custom IP & DNS");


        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        chooseMe(choice);

    }
}
