/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class SuperListenerv1 extends ListenerAdapter {
    
    boolean toggledSpaces = false, nonEmbed = false;
    String prefix = "+", messageContent, GifLink;
    String[] args;
    Member eventMember;
    Message message;
    TextChannel Channel;
    EmbedBuilder embedBuilder = new EmbedBuilder();
    
    //strg+c & strg+v
    String commands = "```prefix: Gibt den prefix an, in diesem Fall '" + prefix + "'\nblubb: Listet unsere Commands auf.\nhallo: blubb!\nwerist @User: Gibt informationen über den User wieder"
            + "\ncoinflip/münzwurf: Wirft eine Münze\ndice/würfel: Würfelt eine zufüllige Zahl\ngarticphone/gp: Link für Gartic Phone\ncardsagainsthumanity/cah: Link für Cards Against Humanity"
            + "\nquote: Zitiert dich selbst\nstats: Zeigt die Statistik eines Nutzers an\nvaried: nA wAs MaChT dAs WoHl?\nohrwurm: Drei Chinesen mit dem Kontrabass\ngiveaway: Erlaubt es dir etwas an andere zu verschenken"
            + "\numfrage: Erlaubt es dir eine Umfrage aus zwei Optionen zu erstellen\ntoggle space(s): (De)Aktiviert die Leerzeichenersetzung```";
    String registredGifs = "```\nrawr\nyallah\negal\nohgod```";
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws IndexOutOfBoundsException {

        if (event.isFromType(ChannelType.TEXT) && event.getMessage().getAttachments().size() == 0) {
            if (!(event.getAuthor().isBot())) {
                eventMember = event.getMember();
                message = event.getMessage();
                Channel = event.getTextChannel();
                
                embedBuilder.clear();
                embedBuilder.setTitle("Blubbspinat Bot");
                embedBuilder.setFooter("+blubb");
                embedBuilder.setColor(Color.GREEN);

                messageContent = event.getMessage().getContentDisplay();

                if (toggledSpaces && messageContent.contains("  ")) {
                    String input = messageContent;
                    int counter = 0;
                    while (input.contains("  ")) {
                        input = input.replaceFirst(" {2}", " ");
                        counter++;
                    }
                    embedBuilder.addField(event.getMember().getEffectiveName() + " wollte:", input + "\n\n sagen und nutze dabei `" + counter + "` Leerzeichen zu viel :man_facepalming:.", false);
                    Channel.deleteMessageById(message.getId()).queue();
                    Channel.sendMessage(embedBuilder.build()).queue();
                }
                
                if (messageContent.equalsIgnoreCase("prefix")) {
                    embedBuilder.addField("Prefix", "Unser prefix ist '" + prefix + "'", false);

                    Channel.deleteMessageById(message.getId()).queue();
                    Channel.sendMessage(embedBuilder.build()).queue();
                }

                if (messageContent.startsWith(prefix)) {
                    args = messageContent.substring(1).toLowerCase().split(" ");

                    handleCommands(event, args);
                    Channel.deleteMessageById(message.getId()).queue();
                }
            }
        }
    }
    
    void handleCommands(MessageReceivedEvent event, String[] command) {
        //switch > if
        switch (command.length) {
            case 1:
                switch (command[0]) {
                    case "v":
                    case "version":
                        embedBuilder.addField("Version", "2.03", false);
                        break;
                    case "blubb":
                        embedBuilder.addField("Commands", commands, false);
                        break;
                    case "hallo":
                        embedBuilder.addField("Blubb", "blubb blubb blubb!", false);
                        break;
                    case "garticphone":
                    case "gp":
                        embedBuilder.addField("Gartic Phone - Stille Post", "https://garticphone.com/de", false);
                        break;
                    case "cardsagainsthumanity":
                    case "cah":
                        embedBuilder.addField("Cards against Humanity", "https://picturecards.online/static/index.html", false);
                        break;
                    case "codenames":
                    case "cn":
                        embedBuilder.addField("Codenames", "https://codenames.game/", false);
                        break;
                    case "quote":
                        embedBuilder.addField("", messageContent.substring(7), false);
                        embedBuilder.addField("~" + eventMember.getEffectiveName(), "", false);
                    case "stats":
                    case "memberstats":
                        memberStats();
                        break;
                    case "varied":
                        String message = messageContent.substring(8);
                        String[] everyChar = message.split("");
                        String outgoingMessage = ""; //darf nicht null sein, muss als "" deklariert werden
                        String einChar;

                        for (int i = 0; i < everyChar.length; i++) {
                            if (i % 2 == 1) {
                                einChar = everyChar[i].toUpperCase();
                            } else {
                                einChar = everyChar[i].toLowerCase();
                            }
                            outgoingMessage += einChar;
                        }

                        embedBuilder.addField("Varieded Message:", "\n" + outgoingMessage, false);
                        break;
                    case "ohrwurm":
                        embedBuilder.addField("3 Giraffen mit nem Kontrabass", "Drei Giraffen mit dem Kontrabass\nsaßen auf der Straße und erzählten sich was\n"
                                    + "Da kamen die Elefanten, fragten 'Was ist denn das?'\nDrei Giraffen mit dem Kontrabass!", false);
                        break;
                    case "frühstück":
                        Member BETTER_member123 = event.getGuild().getMemberById("787709661842505748");
                            
                            embedBuilder.addField("Frühstück", "Also Frühstück ist wenn zwei Individuuen einer Tierart (oder unterschiedlicher Tierarten) sich zu einem nächtlichen oder täglichen Beischlaf treffen, um die Möglichkeit der Fortpflanzung herbeizuführen oder  skandalöserweise einfach Spaß zu haben", false);
                            embedBuilder.addField("~" + BETTER_member123.getEffectiveName(), "https://discord.com/channels/788794254125432849/790513198662680616/824921467002028032", false);
                            break;
                    case "münzwürf":
                    case "coinflip":
                        luckHandler(event, "coin");
                        break;
                    case "würfel":
                    case "dice":
                        luckHandler(event, "dice");
                        break;
                        
                    default:
                        embedBuilder.addField(":thinking: hmmm", "Ups, da lief was schief. Ich kenne diesen Command nicht", false);
                }
                break;
            case 2:
                switch (command[0]) {
                    case "stats":
                    case "memberstats":
                        memberStats();
                        break;
                    case "dice":
                    case "würfel":
                        luckHandler(event, "customdice");
                        break;
                    case "toggle":
                        switch (command[1]) {
                            case "space":
                            case "spaces":
                                toggledSpaces = !toggledSpaces;
                                embedBuilder.addField("Leerzeichenersetzung", "Du hast die Leerzeichenersetzung nun auf `" + toggledSpaces + "` gesetzt", false);
                                break;
                        }
                        break;
                    case "delete":
                        int amount = Integer.parseInt(command[1]);
                        if (2 >= amount && amount <= 50) {
                            List<Message> messages = Channel.getHistory().retrievePast(amount).complete();
                            Channel.deleteMessages(messages).complete();
                        }
                        break;
                    case "gif":
                    case "gifs":
                        gifHandler(event, command[1]);
                        break;
                    case "werist":
                        weristHandler(event);
                        break;
                }
                break;
                
            default:
                embedBuilder.addField(":thinking: hmmm", "Ups, da lief was schief. Ich kenne diesen Command nicht", false);
                
        }
        if (!nonEmbed) {
        Channel.sendMessage(embedBuilder.build()).queue();
        } else {
            nonEmbed = false;
        }
        //Nachricht muss am ende des Switches gesendet werden. Ausnahmefälle sowie unbekannte Commands müssen mit einer Fehlermeldung ausgegeben werden
        //embedBuilder.addField(":thinking: hmmm", "Ups, da lief was schief. Ich kenne diesen Command nicht", false);
        
        }
    
    void gifHandler(MessageReceivedEvent event, String gifName) {
        GifLink = "default";
        switch(gifName) {
            case "help":
                embedBuilder.addField("Gif Manager", "Unsere registrierten Commands für Gifs sind: \n" + registredGifs, false);
                break;
            case "rawr":
                GifLink = "https://tenor.com/view/satan-kitty-strangers-devil-candy-gif-16092089";
                break;
            case "yallah":
                GifLink = "https://tenor.com/view/yallah-dwayne-johnson-pointing-gif-14679728";
                break;
            case "egal":
                GifLink = "https://tenor.com/view/egal-singing-ocean-sea-boat-ride-gif-16080257";
                break;
            case "ohgod":
                GifLink = "https://tenor.com/view/shame-regret-panda-ohgod-whathaveidone-gif-4520297";
            default:
                embedBuilder.addField("Gif Manager", "Dieser Command ist mit keinem Gif verknüpft. Für eine Auflistung unserer Gifs nutze +gif help", false);
        }
        switch (GifLink) {
            case "default":
                break;
            default:
                Channel.sendMessage(GifLink).queue();
                nonEmbed = true;
        }
    }
        
    void luckHandler(MessageReceivedEvent event, String type) {
        Random random = new Random();
        int randomNumber;
        switch (type) {
            case "coin":
                String outcome;
                
                randomNumber = new Random().nextInt(101) - 1;
                
                switch (randomNumber % 2) {
                    case 0:
                        outcome = "Kopf";
                        break;
                    case 1:
                        outcome = "Zahl";
                        break;
                    default:
                        outcome = "äähhhhm, das hätte nicht passieren sollen";
                }
                embedBuilder.addField("Ergebnis des Münzwurfs: ", "**" + outcome + "**", false);
                break;
            case "dice":
                randomNumber = random.nextInt(6) + 1;
                embedBuilder.addField("Würfelwurf:", Integer.toString(randomNumber), false);
                break;
            case "customdice":
                int randomNumer = random.nextInt(Integer.parseInt(args[1]));
                embedBuilder.addField("Würfelwurf:", Integer.toString(randomNumer), false);
                break;
        }
    }
    
    void weristHandler(MessageReceivedEvent event) {
        String member = event.getMessage().getContentRaw().replaceAll("[<@!>]", "").toLowerCase();
        switch (member) {
            case "jan":
            case "schnebi":
            case "264396390199721984":
                embedBuilder.addField("Jan Schneeberg", "Bester Developer, bescheiden, Basketballer, 2.04m groß, Lauch, versucht das Thema von <@688816780810125413> Undercover Podcasts herauszufinden.", false);
                System.out.println("WerIstListener: Member: Jan Schneeberg");
            break;
            case "lea":
            case "688816780810125413":
                embedBuilder.addField("Lea", "Chefin, Eigentürmerin des Servers, <@&788822739556237353>, trotzdem klein und laut, CIA Agentin, hat ein Undercover Podcast, kommt aus Rohracker", false);
                embedBuilder.addField("Hogwartshaus", "Slytherin", false);
                System.out.println("WerIstListener: Member: Lea");
                break;
            case "moritz":
            case "694873278656938034":
                embedBuilder.addField("Moritz Breitenbach", "Saxophon", false);
                System.out.println("WerIstListener: Member: Moritz Breitenbach");
                break;
            case "runa":
            case "weber":
            case "runa weber":
            case "789479515495333889":
                embedBuilder.addField("Runa Weber", "ahnungslos, 'Hobbies wie Theater tanzen singen'", false);
                System.out.println("WerIstListener: Member: Runa Weber");
                break;
            case "konrad":
            case "604265581340000273":
                embedBuilder.addField("Konrad Olliver Oscar-Gustav", "Örtlicher IT-Support", false);
                System.out.println("WerIstListener: Member: Konrad Harnisch-John");
                break;
            case "julian":
            case "787709661842505748":
                embedBuilder.addField("Julian", "Katzenhasser, Stock, Herr der Ringe Fanboy und leidenschaftlicher Toilettentieftaucher", false);
                System.out.println("WerIstListener: Member: Julian");
                break;
            case "finn":
            case "279268029131390977":
                embedBuilder.addField("Finn", "Der beste und seriöseste Chefredakteur den die Welt je gesehen hat, Stupedidianer, Verfechter der Tommy-Ideologie", false);
                System.out.println("WerIstListener: Member: Finn");
                break;
            case "pascal":
            case "278972830433935371":
                embedBuilder.addField("Unbekannter", "Ein mystischer Unbekannter welcher sich aus der Datenbank hat löschen lassen um anonym zu bleiben", false);
                System.out.println("WerIstListener: Member: Pascal");
                break;
            case "pia":
            case "413680889365528577":
                embedBuilder.addField("Pia", "verrückter Scheinriese der Musical addicted ist", false);
                System.out.println("WerIstListener: Member: Pia");
                break;
            case "magda":
            case "787713619570982934":
                embedBuilder.addField("Magda", " ", false);
                System.out.println("WerIstListener: Member: Magda");
                break;
            case "laura":
            case "778704706734260245":
                embedBuilder.addField("Laura", "die verlächeltste Person auf dem Server", false);
                System.out.println("WerIstListener: Member: Laura");
                break;
            case "jakob":
            case "760144337542709318":
                embedBuilder.addField("Jakob", "Kanisterkopf, Micheal Jordan v2, Blondie mit langen Haaren, wohnt in Kemnat, mag die Ära des Deutschen Kaiserreichs, Nachfahre Bismarcks, liebt Gollum, Gryffindor", false);
                System.out.println("WerIstListener: Member: Jakob");
                break;
            case "julie":
            case "789411377118445598":
                embedBuilder.addField("Julie", "Kaktusliebende Gingerhexe die in ihrem Kaktuszirkel ihre Zaubertänze ausführt", false);
                embedBuilder.addField("Hogwartshaus", "Hufflepuff", false);
                System.out.println("WerIstListener: Member: Julie");
                break;
            case "lukas":
            case "683360085178974257":
                embedBuilder.addField("Lucas", " ", false);
                System.out.println("WerIstListener: Member: Lucas");
                break;
            case "heni":
            case "hendrick":
            case "534078759444283412":
                embedBuilder.addField("Hendrick 'Heni'", "Professioneller Hypixel Skyblock Tryhard", false);
                System.out.println("WerIstListener: Member: Hendrick");
                break;
            case "fabian":
            case "475405721421479957":
                embedBuilder.addField("Fabian", " ", false);
                System.out.println("WerIstListener: Member: Fabian");
                break; 
            case "hannah":
            case "797196765647339572":
                embedBuilder.addField("Hannah", " ", false);
                System.out.println("WerIstListener: Member: Hannah");
                break; 
            case "leonie":
            case "718449430306750495":
                embedBuilder.addField("Leonie", " ", false);
                System.out.println("WerIstListener: Member: Leonie");
                break; 
            case "franzi":
            case "787712469429911562":
                embedBuilder.addField("Franzi", " ", false);
                System.out.println("WerIstListener: Member: Franzi");
                break; 
            case "fränz":
            case "787717075581927465":
                embedBuilder.addField("Fränz", " ", false);
                System.out.println("WerIstListener: Member: Fränz");
                break; 
            case "lara":
            case "694521636518494219":
                embedBuilder.addField("Lara", " ", false);
                System.out.println("WerIstListener: Member: Lara");
                break; 
            case "miri":
            case "771473638335316050":
                embedBuilder.addField("Miri", " ", false);
                System.out.println("WerIstListener: Member: Miri");
                break;
            case "rebecca":
            case "690590818754494565":
                embedBuilder.addField("Rebecca", " ", false);
                System.out.println("WerIstListener: Member: Rebecca");
                break;
            case "jasper":
            case "233603431934328833":
                embedBuilder.addField("Jasper", "Ein Pinguin der gerne mit GNUs chillt", false);
                System.out.println("WerIstListener: Member: Jasper");
                break;
            default:
                embedBuilder.addField("Unbekannter", "Ein mystischer Unbekannter welchen ich nicht in meiner Datenbank finde. Sollte es sich um einen Fehler handeln benachrichtige bitte <@264396390199721984>", false);
                break;
        }
    }
    
    void memberStats() {
        if (args.length == 1) {
            embedBuilder.addField("User: ", eventMember.getEffectiveName(), false);
            embedBuilder.addField("ID", eventMember.getId(), false);
            //
            //Created
            LocalDate TimeCreatedLocalDate = eventMember.getTimeCreated().toLocalDate();
            LocalTime TimeCreatedLocalTime = (eventMember.getTimeCreated().toLocalTime());

            String[] TimeArgs = TimeCreatedLocalDate.toString().split("-");
            String TimeCreated = TimeArgs[2] + "." + TimeArgs[1] + "." + TimeArgs[0];

            embedBuilder.addField("Userprofil erstellt:", TimeCreated + "\n" + TimeCreatedLocalTime.toString().substring(0, 8), false);

            //
            //Joined
            LocalDate TimeJoinedLocalDate = eventMember.getTimeJoined().toLocalDate();
            LocalTime TimeJoinedLocalTime = eventMember.getTimeJoined().toLocalTime();

            String[] TimeArgs2 = TimeJoinedLocalDate.toString().split("-");
            String TimeJoined = TimeArgs2[2] + "." + TimeArgs2[1] + "." + TimeArgs2[0];

            embedBuilder.addField("Dem Server beigetreten:", TimeJoined + "\n" + TimeJoinedLocalTime.toString().substring(0, 8), false);

            //
            //OnGuildSince
            //                       Ja  Fe  Ma  Ap  Ma Jun Jul  Au Sep  Ok  No  De
            int[] monthsInDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            //declaring shit
            LocalDate DateNow, DateJoinedGuild;

            //initialising the DATE
            DateNow = LocalDate.now();
            DateJoinedGuild = eventMember.getTimeJoined().toLocalDate();

            //getting Day, Month and Year
            int DateNowDay = DateNow.getDayOfMonth(), DateNowMonth = DateNow.getMonthValue(), DateNowYear = DateNow.getYear();
            int DateJoinedGuildDay = DateJoinedGuild.getDayOfMonth(), DateJoinedGuildMonth = DateJoinedGuild.getMonthValue(), DateJoinedGuildYear = DateJoinedGuild.getYear();

            int DateOnServerYear, DateOnServerMonth, DateOnServerDay;

            //Calculating Time on Guild in Date
            DateOnServerDay = DateNowDay - DateJoinedGuildDay;
            DateOnServerMonth = DateNowMonth - DateJoinedGuildMonth;
            DateOnServerYear = DateNowYear - DateJoinedGuildYear;

            if (DateOnServerDay < 0) {
                DateOnServerDay = DateOnServerDay + monthsInDays[DateJoinedGuildMonth];
                DateOnServerMonth = DateOnServerMonth - 1;
            }
            if (DateOnServerMonth < 0) {
                DateOnServerMonth = DateOnServerMonth + 12;
                DateOnServerYear = DateOnServerYear - 1;
            }

            int JahrNow = DateNowYear, JahrPast = DateJoinedGuildYear;
            int[] Jahre = (IntStream.rangeClosed(JahrPast, JahrNow).toArray());

            for (int j : Jahre) {
                if (j % 4 == 0) {
                    DateOnServerDay++;
                }
            }
            String DateOnServer = "";

            String strYear = " Jahren", strMonth = " Monaten und ", strDay = " Tagen";
            if (DateOnServerYear == 1) {
                strYear = " Jahr";
            }
            if (DateOnServerMonth == 1) {
                strMonth = " Monat und ";
            }
            if (DateOnServerDay == 1) {
                strDay = " Tag";
            }

            if (DateOnServerYear > 0) {
                DateOnServer = DateOnServerYear + strYear + DateOnServerMonth + strMonth + DateOnServerDay + strDay;
            } else if (DateOnServerMonth > 0) {
                DateOnServer = DateOnServerMonth + strMonth + DateOnServerDay + strDay;
            } else if (DateOnServerDay > 0) {
                DateOnServer = DateOnServerDay + strDay;
            }

            embedBuilder.addField("Auf dem Server seit:", DateOnServer, true);
            //Message send
            System.out.println("CommandListener: Detected message 'stats' with argslength of 1. Responding by to User " + eventMember.getEffectiveName() + " by giving following stats: \n" + TimeCreated + "\n"
                    + TimeCreatedLocalTime.toString().substring(0, 8) + "\n" + TimeJoined + "\n" + TimeJoinedLocalTime.toString().substring(0, 8) + "\nmay be not correct idk didnt rework this");
        } else if (args.length == 2) {
            List<Member> TaggedUsers = message.getMentionedMembers();
            Member MemberStats = TaggedUsers.get(0);

            if (MemberStats.hasPermission(Permission.VIEW_CHANNEL)) {

                embedBuilder.addField("User: ", MemberStats.getEffectiveName(), false);
                embedBuilder.addField("ID", MemberStats.getId(), false);
                //                        
                //Created
                LocalDate TimeCreatedLocalDate = MemberStats.getTimeCreated().toLocalDate();
                LocalTime TimeCreatedLocalTime = MemberStats.getTimeCreated().toLocalTime();

                String[] TimeArgs = TimeCreatedLocalDate.toString().split("-");
                String TimeCreated = TimeArgs[2] + "." + TimeArgs[1] + "." + TimeArgs[0];

                embedBuilder.addField("Userprofil erstellt:", TimeCreated + "\n" + TimeCreatedLocalTime.toString().substring(0, 8), false);

                //
                //Joined
                LocalDate TimeJoinedLocalDate = MemberStats.getTimeJoined().toLocalDate();
                LocalTime TimeJoinedLocalTime = MemberStats.getTimeJoined().toLocalTime();

                String[] TimeArgs2 = TimeJoinedLocalDate.toString().split("-");
                String TimeJoined = TimeArgs2[2] + "." + TimeArgs2[1] + "." + TimeArgs2[0];

                embedBuilder.addField("Dem Server beigetreten:", TimeJoined + "\n" + TimeJoinedLocalTime.toString().substring(0, 8), false);

                //
                //OnGuildSince
                //                       Ja  Fe  Ma  Ap  Ma Jun Jul  Au Sep  Ok  No  De
                int[] monthsInDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

                //declaring shit
                LocalDate DateNow, DateJoinedGuild;

                //initialising the DATE
                DateNow = LocalDate.now();
                DateJoinedGuild = MemberStats.getTimeJoined().toLocalDate();

                //getting Day, Month and Year
                int DateNowDay = DateNow.getDayOfMonth(), DateNowMonth = DateNow.getMonthValue(), DateNowYear = DateNow.getYear();
                int DateJoinedGuildDay = DateJoinedGuild.getDayOfMonth(), DateJoinedGuildMonth = DateJoinedGuild.getMonthValue(), DateJoinedGuildYear = DateJoinedGuild.getYear();

                int DateOnServerYear = 0, DateOnServerMonth = 0, DateOnServerDay = 0;

                //Calculating Time on Guild in Date 
                DateOnServerDay = DateNowDay - DateJoinedGuildDay;
                DateOnServerMonth = DateNowMonth - DateJoinedGuildMonth;
                DateOnServerYear = DateNowYear - DateJoinedGuildYear;

                if (DateOnServerDay < 0) {
                    DateOnServerDay = DateOnServerDay + monthsInDays[DateJoinedGuildMonth];
                    DateOnServerMonth = DateOnServerMonth - 1;
                }
                if (DateOnServerMonth < 0) {
                    DateOnServerMonth = DateOnServerMonth + 12;
                    DateOnServerYear = DateOnServerYear - 1;
                }

                int JahrNow = DateNowYear, JahrPast = DateJoinedGuildYear;
                int[] Jahre = (IntStream.rangeClosed(JahrPast, JahrNow).toArray());

                for (int j : Jahre) {
                    if (j % 4 == 0) {
                        DateOnServerDay++;
                    }
                }

                String DateOnServer = "";

                String strYear = " Jahren", strMonth = " Monaten und ", strDay = " Tagen";
                if (DateOnServerYear == 1) {
                    strYear = " Jahr";
                }
                if (DateOnServerMonth == 1) {
                    strMonth = " Monat und ";
                }
                if (DateOnServerDay == 1) {
                    strDay = " Tag";
                }

                if (DateOnServerYear > 0) {
                    DateOnServer = DateOnServerYear + strYear + DateOnServerMonth + strMonth + DateOnServerDay + strDay;
                } else if (DateOnServerMonth > 0) {
                    DateOnServer = DateOnServerMonth + strMonth + DateOnServerDay + strDay;
                } else if (DateOnServerDay > 0) {
                    DateOnServer = DateOnServerDay + strDay;
                }

                embedBuilder.addField("Auf dem Server seit:", DateOnServer, true);

                //Message send
                System.out.println("CommandListener: Detected message 'stats' with argslength of 1. Responding by to User " + eventMember.getEffectiveName() + " by giving following stats of "
                        + MemberStats.getNickname() + ": \n" + TimeCreated + "\n" + TimeCreatedLocalTime.toString().substring(0, 8) + "\n" + TimeJoined + "\n" + TimeJoinedLocalTime.toString().substring(0, 8));
            }
        }
    }
}
