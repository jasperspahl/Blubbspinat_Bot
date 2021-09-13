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
    public static int time, milisecsToSec = 1000;
    public static long timeInMS;
    public static List<Member> participants = new ArrayList<>();
    public static Timer timer = new Timer();
    public static Timer timerReveal = new Timer();
    public static Timer timerNr2 = new Timer();

    public static String messageID;
    public static boolean running = false;

    @Override

    public void onMessageReceived(MessageReceivedEvent event) {
        embedBuilder.setTitle("Giveaway by");
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setFooter("+blubb");
        embedBuilder.clearFields();

        
        String[] args = event.getMessage().getContentDisplay().split(" ");
        String messageContent = event.getMessage().getContentDisplay();
        if (args[0].equalsIgnoreCase("+giveaway")) {
            if (running) {
                embedBuilder.addField("Giveaway am laufen", "Es ist bereits ein Giveaway am laufen. Bitte warte bis das Giveaway zu Ende ist", false);
                event.getChannel().sendMessage(embedBuilder.build()).queue();
            } else {
                try {
                    item = messageContent.substring(10, messageContent.length() - args[args.length - 1].length() - 1);

                    time = Integer.parseInt(args[args.length - 1].substring(0, args[args.length - 1].length() - 1));
                    unit = args[args.length - 1].substring(args[args.length - 1].length() - 1, args[args.length - 1].length());

                    if (unit.equalsIgnoreCase("s")) {
                        timeInMS = (long) time * milisecsToSec;
                    } else if (unit.equalsIgnoreCase("m")) {
                        timeInMS = (long) time * 60 * milisecsToSec;
                    } else if (unit.equalsIgnoreCase("h")) {
                        timeInMS = (long) time * 60 * 60 * milisecsToSec;
                    } else if (unit.equalsIgnoreCase("d")) {
                        timeInMS = (long) 24 * 60 * 60 * milisecsToSec;
                    }

                    embedBuilder.addField(event.getMember().getEffectiveName(), "Zu gewinnen gibt es '" + item + "'. Reagiert auf diese Nachricht um als Teilnehmer registriert zu werden\n", false);

                    timer = new Timer();
                    timerNr2 = new Timer();

                    event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                        message.addReaction("U+1F389").queue();
                        messageID = message.getId();

                        timerNr2.scheduleAtFixedRate(new TimerTask() {
                            public void run() {
                                embedBuilder.clearFields();
                                embedBuilder.addField(event.getMember().getEffectiveName(), "Zu gewinnen gibt es '" + item + "'. Reagiert auf diese Nachricht um als Teilnehmer registriert zu werden\n", false);
                                embedBuilder.addField("Verbleibende Zeit", convertmillis(timeInMS), false);
                                if (timeInMS > 1) {
                                    message.editMessage(embedBuilder.build()).queue();
                                }
                                timeInMS = timeInMS - 5 * milisecsToSec;
                            }
                        }, 0, 5 * milisecsToSec);

                        timer.schedule(new TimerTask() {
                            public void run() {
                                message.delete().queue();
                                timerNr2.cancel();
                                timerNr2.purge();
                            }
                        }, timeInMS);
                    });

                    running = true;

                    timerReveal = new Timer();
                    timerReveal.schedule(new TimerTask() {
                        public void run() {
                            Random random = new Random();

                            embedBuilder.clearFields();

                            if (participants.size() > 0) {
                                int randomNumer = random.nextInt(participants.size());
                                embedBuilder.addField(event.getMember().getEffectiveName(), "Der Gewinner des Giveaways von " + item + " ist " + participants.get(randomNumer).getAsMention()
                                        + "!\nGlückwunsch an ihn und viel Glück beim nächsten Mahl an die anderen " + (participants.size() - 1) + " Teilnehmer!", false);
                            } else {
                                embedBuilder.addField("Niemand", "Bedauerlicher weise hat sich niemand als Gewinner für " + item + " registriert. Vielleicht beim nächsten mal", false);
                            }

                            event.getChannel().sendMessage(embedBuilder.build()).queue();
                            running = false;
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
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent ReactionAddEvent) {
        if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+1f389") && ReactionAddEvent.getMessageId().equals(messageID) && !ReactionAddEvent.getUser().isBot()) {
            participants.add(ReactionAddEvent.getMember());
            System.out.println("GiveawayListener: Added User to participants, user is " + ReactionAddEvent.getMember());
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent ReactionRemoveEvent) {
        if (ReactionRemoveEvent.getReactionEmote().getAsCodepoints().equals("U+1f389") && ReactionRemoveEvent.getMessageId().equals(messageID) && !ReactionRemoveEvent.getUser().isBot()) {
            participants.remove(ReactionRemoveEvent.getMember());
            System.out.println("GiveawayListener: Removed User to participants, user is " + ReactionRemoveEvent.getMember());
        }
    }

    public static String convertmillis(long input) {
        int days = 0, hours = 0, minutes = 0, seconds = 0, millis;

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
