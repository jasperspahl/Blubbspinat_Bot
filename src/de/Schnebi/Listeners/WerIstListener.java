/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class WerIstListener extends ListenerAdapter {

    public String prefix = "+";
    public EmbedBuilder embedBuilder = new EmbedBuilder();
    public String BannedUsers = " ";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws IndexOutOfBoundsException {

        if (event.isFromType(ChannelType.TEXT) && event.getMessage().getAttachments().size() == 0) {
            if (!(event.getAuthor().isBot())) {
                if (!(BannedUsers.contains(event.getMember().getId()))) {
                TextChannel channel = event.getTextChannel();
                Member member = event.getMember();

                String messageContentDisplay = event.getMessage().getContentDisplay();

                String[] args;
                String messagePrefix;
                args = messageContentDisplay.substring(1).split(" ");
                messagePrefix = Character.toString(messageContentDisplay.charAt(0));

                if (messagePrefix.equalsIgnoreCase("+")) {
                    if (args[0].equalsIgnoreCase("werist")) {
                        
                        System.out.println("\nWerIstListener: Received Message '+werist'");
                        String memberId = event.getMessage().getContentRaw().substring(8);
                        System.out.println("WerIstListener: memberId = " + memberId);
                        memberId = memberId.replaceAll("[<@!>]", "");
                        memberId = memberId.toLowerCase();
                        System.out.println("WerIstListener: memberId after removel = " + memberId);

                        embedBuilder.clear();
                        embedBuilder.setTitle("Blubbspinat Bot");
                        embedBuilder.setFooter("+blubb");
                        embedBuilder.setColor(Color.GREEN);
                        String defaultString = "Der User hat noch nichts hinterlegt. Sollte es sich um einen Fehler handeln oder sollte etwas hinzugefügt werden benachrichtige bitte <@264396390199721984>";
                        

                        switch (memberId) {
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
                            channel.sendMessage(embedBuilder.build()).queue();
                        }
                    }
                }
            }
        }
    }
}