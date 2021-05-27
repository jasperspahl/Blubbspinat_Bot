//Command: +giveaway [item] [dauer]

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class GiveawayListener extends ListenerAdapter {

    public static EmbedBuilder embedBuilder = new EmbedBuilder();

    public static String item, unit;
    public static int time, secondMS = 1000;
    public static long timeInMS;
    public static List<Member> participants = new ArrayList<Member>();
    public static Timer timer = new Timer();
    public static Timer timerReveal = new Timer();
    public static Timer timerNr2 = new Timer();

    public static String messageID;

    public static boolean giveawayRunning = false;

    @Override

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentDisplay().split(" ");
        String messageContent = event.getMessage().getContentDisplay();
        if (args[0].equalsIgnoreCase("+giveaway")) {
            event.getChannel().deleteMessageById(event.getMessageId()).queue();
            try {
                item = messageContent.substring(10, messageContent.length() - args[args.length - 1].length() - 1);

                time = Integer.parseInt(args[args.length - 1].substring(0, args[args.length - 1].length() - 1));
                unit = args[args.length - 1].substring(args[args.length - 1].length() - 1, args[args.length - 1].length());

                if (unit.equalsIgnoreCase("s")) {
                    timeInMS = time * secondMS;
                } else if (unit.equalsIgnoreCase("m")) {
                    timeInMS = time * 60 * secondMS;
                } else if (unit.equalsIgnoreCase("h")) {
                    timeInMS = time * 60 * 60 * secondMS;
                } else if (unit.equalsIgnoreCase("d")) {
                    timeInMS = 24 * 60 * 60 * secondMS;
                }

                embedBuilder.clear();
                embedBuilder.setTitle("Giveaway by");
                embedBuilder.setColor(Color.GREEN);
                embedBuilder.setFooter("+blubb");
                embedBuilder.addField(event.getMember().getEffectiveName(), "Zu gewinnen gibt es '" + item + "'. Reagiert auf diese Nachricht um als Teilnehmer registriert zu werden\n", false);
                
                timer = new Timer();
                timerNr2 = new Timer();

                event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                    message.addReaction("U+1F389").queue();
                    messageID = message.getId();
                    
                    timerNr2.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            embedBuilder.clear();
                            embedBuilder.setFooter("+blubb");
                            embedBuilder.setColor(Color.GREEN);
                            embedBuilder.setTitle("Giveaway by");
                            embedBuilder.addField(event.getMember().getEffectiveName(), "Zu gewinnen gibt es '" + item + "'. Reagiert auf diese Nachricht um als Teilnehmer registriert zu werden\n", false);
                            embedBuilder.addField("Verbleibende Zeit", convertmillis(timeInMS), false);
                            message.editMessage(embedBuilder.build()).queue();
                            timeInMS = timeInMS - 5 * secondMS;
                        }
                    }, 0 ,5 * secondMS);
                    
                    timer.schedule(new TimerTask() {
                        public void run() {
                            message.delete().queue();
                            timerNr2.cancel();
                            timerNr2.purge();
                        }
                    }, timeInMS);
                });

                timerReveal = new Timer();
                timerReveal.schedule(new TimerTask() {
                    public void run() {
                        Random random = new Random();
                        int randomNumer = random.nextInt(participants.size());
                        embedBuilder.clear();
                        embedBuilder.setTitle("Giveaway by");
                        embedBuilder.setFooter("+blubb");
                        embedBuilder.setColor(Color.GREEN);
                        embedBuilder.addField(event.getMember().getEffectiveName(), "Der Gewinner des Giveaways von " + item + " ist " + participants.get(randomNumer).getAsMention()
                                + "!\nGlückwunsch an ihn und viel Glück beim nächsten Mahl an die anderen " + (participants.size() - 1) + " Teilnehmer!", false);

                        event.getChannel().sendMessage(embedBuilder.build()).queue();
                    }
                }, timeInMS);
            } catch (Exception e) {
                embedBuilder.clear();
                embedBuilder.setTitle("Blubbspinat Bot");
                embedBuilder.setFooter("+blubb");
                embedBuilder.setColor(Color.GREEN);
                embedBuilder.addField("Fehler", "Falsche Verwendung des Commands.\nKorrekte Verwendung: +giveaway [item] [dauer]. \nDauer kann in Minuten (m), Stunden (h) oder Tagen (d) angegeben werden.", false);
                event.getChannel().sendMessage(embedBuilder.build()).queue();
            }
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent ReactionAddEvent) {
        if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+1f389") && ReactionAddEvent.getMessageId().equals(messageID) && !ReactionAddEvent.getUser().isBot()) {
            participants.add(ReactionAddEvent.getMember());
            System.out.println("Added User to participants, user is " + ReactionAddEvent.getMember());
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent ReactionRemoveEvent) {
        if (ReactionRemoveEvent.getReactionEmote().getAsCodepoints().equals("U+1f389") && ReactionRemoveEvent.getMessageId().equals(messageID) && !ReactionRemoveEvent.getUser().isBot()) {
            participants.remove(ReactionRemoveEvent.getMember());
            System.out.println("Removed User to participants, user is " + ReactionRemoveEvent.getMember());
        }
    }
    
    public static String convertmillis(long input) {
        int days = 0, hours = 0, minutes = 0, seconds = 0, millis = 0;

        int day = 86400000;
        int hour = 3600000;
        int minute = 60000;
        int second = 1000;

        if (input >= day) {
            days = (int) (input / day);
            millis = (int) (input % day);
        } else {
            millis = (int) input;
        }

        if (millis >= hour) {
            hours = millis / hour;
            millis = millis % hour;
        }

        if (millis >= minute) {
            minutes = millis / minute;
            millis = millis % minute;
        }

        if (millis >= second) {
            seconds = millis / second;
            millis = millis % second;
        }

        return (days + "d, " + hours + "h, " + minutes + "min, " + seconds + "s");
    }
}
