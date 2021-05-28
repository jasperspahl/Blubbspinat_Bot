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
import java.util.stream.IntStream;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

/**
 *
 * @author jansc
 */
public class CommandListener extends ListenerAdapter {

    public String prefix = "+";
    public EmbedBuilder embedBuilder = new EmbedBuilder();
    public String BannedUsers = " ";
    public boolean toggledSpaces = false;
    public String[] args;

    public String commands = "```prefix: Gibt den prefix an, in diesem Fall '" + prefix + "'\nblubb: Listet unsere Commands auf.\nhallo: blubb!\nwerist @User: Gibt informationen über den User wieder"
            + "\ncoinflip/münzwurf: Wirft eine Münze\ndice/würfel: Würfelt eine zufüllige Zahl\ngarticphone/gp: Link für Gartic Phone\ncardsagainsthumanity/cah: Link für Cards Against Humanity"
            + "\nquote: Zitiert dich selbst\nstats: Zeigt die Statistik eines Nutzers an\nvaried: nA wAs MaChT dAs WoHl?\nohrwurm: Drei Chinesen mit dem Kontrabass\ngiveaway: Erlaubt es dir etwas an andere zu verschenken"
            + "\numfrage: Erlaubt es dir eine Umfrage aus zwei Optionen zu erstellen```";

    private void playerStats(MessageReceivedEvent event) {
        if (args.length == 1) {
            embedBuilder.addField("User: ", event.getMember().getEffectiveName(), false);
            embedBuilder.addField("ID", event.getMember().getId(), false);
            //
            //Created
            LocalDate TimeCreatedLocalDate = event.getMember().getTimeCreated().toLocalDate();
            LocalTime TimeCreatedLocalTime = (event.getMember().getTimeCreated().toLocalTime());

            String[] TimeArgs = TimeCreatedLocalDate.toString().split("-");
            String TimeCreated = TimeArgs[2] + "." + TimeArgs[1] + "." + TimeArgs[0];

            embedBuilder.addField("Userprofil erstellt:", TimeCreated + "\n" + TimeCreatedLocalTime.toString().substring(0, 8), false);

            //
            //Joined
            LocalDate TimeJoinedLocalDate = event.getMember().getTimeJoined().toLocalDate();
            LocalTime TimeJoinedLocalTime = event.getMember().getTimeJoined().toLocalTime();

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
            DateJoinedGuild = event.getMember().getTimeJoined().toLocalDate();

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
            event.getTextChannel().sendMessage(embedBuilder.build()).queue();
            System.out.println("CommandListener: Detected message 'stats' with argslength of 1. Responding by to User " + event.getMember().getEffectiveName() + " by giving following stats: \n" + TimeCreated + "\n"
                    + TimeCreatedLocalTime.toString().substring(0, 8) + "\n" + TimeJoined + "\n" + TimeJoinedLocalTime.toString().substring(0, 8) + "\nmay be not correct idk didnt rework this");
        } else if (args.length == 2) {
            List<Member> TaggedUsers = event.getMessage().getMentionedMembers();
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
                event.getChannel().sendMessage(embedBuilder.build()).queue();
                System.out.println("CommandListener: Detected message 'stats' with argslength of 1. Responding by to User " + event.getMember().getEffectiveName() + " by giving following stats of "
                        + MemberStats.getNickname() + ": \n" + TimeCreated + "\n" + TimeCreatedLocalTime.toString().substring(0, 8) + "\n" + TimeJoined + "\n" + TimeJoinedLocalTime.toString().substring(0, 8));
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws IndexOutOfBoundsException {

        if (event.isFromType(ChannelType.TEXT) && event.getMessage().getAttachments().size() == 0) {
            if (!(event.getAuthor().isBot())) {
                if (!(BannedUsers.contains(event.getMember().getId()))) {
                    embedBuilder.clear();
                    embedBuilder.setTitle("Blubbspinat Bot");
                    embedBuilder.setFooter("+blubb");
                    embedBuilder.setColor(Color.GREEN);

                    String messageContentDisplay = event.getMessage().getContentDisplay();
                    String messagePrefix;
                    args = messageContentDisplay.substring(1).split(" ");
                    messagePrefix = Character.toString(messageContentDisplay.charAt(0));

                    TextChannel channel = event.getTextChannel();

                    if (toggledSpaces && messageContentDisplay.contains("  ")) {
                        String input = messageContentDisplay;
                        int counter = 0;
                        while (input.contains("  ")) {
                            input = input.replaceFirst(" {2}", " ");
                            counter++;
                        }
                        embedBuilder.addField(event.getMember().getEffectiveName() + " wollte:", input + "\n\n sagen und nutze dabei `" + counter + "` Leerzeichen zu viel :man_facepalming:.", false);
                        channel.deleteMessageById(event.getMessageId()).queue();
                        channel.sendMessage(embedBuilder.build()).queue();
                    }

                    if (messageContentDisplay.equalsIgnoreCase("prefix")) {
                        System.out.println("CommandListener: Detected message 'prefix'. Answering with given prefix");
                        embedBuilder.addField("Prefix", "Unser prefix ist '" + prefix + "'", false);

                        channel.deleteMessageById(event.getMessageId()).queue();
                        channel.sendMessage(embedBuilder.build()).queue();
                    }

                    if (messagePrefix.equalsIgnoreCase(prefix)) {
                        channel.deleteMessageById(event.getMessageId()).queue();
                        if (args[0].equalsIgnoreCase("blubb")) {
                            System.out.println("CommandListener: Detected message '+blubb'. Responding with given list of commands");
                            embedBuilder.addField("Commands", commands, false);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("hallo")) {
                            System.out.println("CommandListener: Detected message '+hallo'. Responding the hello with blubb");
                            embedBuilder.addField("Blubb", "blubb blubb blubb!", false);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("yallah")) {
                            System.out.println("CommandListener: Detected message '+yallah'. Tagging Users and Responding with YallahGif");
                            String MentionedMemberList;
                            MentionedMemberList = event.getMessage().getContentRaw().substring(8);
                            channel.deleteMessageById(event.getMessageId()).queue();
                            channel.sendMessage(MentionedMemberList + "\nhttps://tenor.com/view/yallah-dwayne-johnson-pointing-gif-14679728").queue();

                        } else if (args[0].equalsIgnoreCase("garticphone") || args[0].equalsIgnoreCase("gp")) {
                            System.out.println("CommandListener: Detected message '" + args[0] + "'. Responding with link to Gartic Phone Webservice");
                            embedBuilder.addField("Garlic Phone - Stille Post", "https://garticphone.com/de", false);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("cardsagainsthumanity") || args[0].equalsIgnoreCase("cah")) {
                            System.out.println("CommandListener: Detected message '" + args[0] + "'. Responding with link to Picturecardsonline");
                            embedBuilder.addField("Cards against Humanity", "https://picturecards.online/static/index.html", false);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("codenames") || args[0].equalsIgnoreCase("cn")) {
                            System.out.println("CommandListener: Detected message '" + args[0] + "'. Responding with link to Codenames");
                            embedBuilder.addField("Codenames", "https://codenames.game/", false);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("quote")) {
                            Member Author = event.getMessage().getMember();
                            String Quote = event.getMessage().getContentDisplay().substring(7);
                            System.out.println("CommandListener: Detected message 'quote'. Responding by quoting given Message: " + Quote);

                            embedBuilder.addField("", Quote, false);
                            embedBuilder.addField("~" + Author.getEffectiveName(), "", false);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("stats") && args.length >= 2) {
                            playerStats(event);
                        } else if (args[0].equalsIgnoreCase("varied")) {
                            String message = event.getMessage().getContentDisplay().substring(8);
                            String[] everyChar = message.split("");
                            String outgoingMessage = "";
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
                            channel.sendMessage(embedBuilder.build()).queue();
                            System.out.println("CommandListener: Detected message 'varied'. Responding User from Input: '" + message + "' with Output of: '" + outgoingMessage + "'");

                        } else if (args[0].equalsIgnoreCase("ohrwurm")) {
                            System.out.println("CommandListener: Detected message '" + args[0] + "'. Responding with Songtext of '3 Giraffen mit nem Kontrabass' - ein Kinderlied");
                            embedBuilder.addField("3 Giraffen mit nem Kontrabass", "Drei Giraffen mit dem Kontrabass\nsaßen auf der Straße und erzählten sich was\n"
                                    + "Da kamen die Elefanten, fragten 'Was ist denn das?'\nDrei Giraffen mit dem Kontrabass!", false);
                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("frühstück")) {
                            System.out.println("CommandListener: Detected message '" + args[0] + "'. Responding with definition of Frühstück");
                            Member BETTER_member123 = event.getGuild().getMemberById("787709661842505748");
                            embedBuilder.addField("Frühstück", "Also Frühstück ist wenn zwei Individuuen einer Tierart (oder unterschiedlicher Tierarten) sich zu einem nächtlichen oder täglichen Beischlaf treffen, um die Möglichkeit der Fortpflanzung herbeizuführen oder  skandalöserweise einfach Spaß zu haben", false);
                            embedBuilder.addField("~" + BETTER_member123.getEffectiveName(), "https://discord.com/channels/788794254125432849/790513198662680616/824921467002028032", false);
                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("delete")) {
                            int amount = Integer.parseInt(args[1]);
                            if (2 >= amount && amount <= 50) {
                                List<Message> messages = channel.getHistory().retrievePast(amount).complete();
                                channel.deleteMessages(messages).complete();
                            }
                        } else if (args[0].equalsIgnoreCase("toggle")) {
                            if (args[1].equalsIgnoreCase("space") || args[1].equalsIgnoreCase("spaces")) {
                                toggledSpaces = !toggledSpaces;
                                embedBuilder.addField("Leerzeichenersetzung", "Du hast die Leerzeichenersetzung nun auf `" + toggledSpaces + "` gesetzt", false);
                                channel.sendMessage(embedBuilder.build()).queue();
                            }
                        }
                    }
                }
            }
        }
    }
}
