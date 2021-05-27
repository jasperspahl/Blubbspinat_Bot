/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class UmfrageListener extends ListenerAdapter {

    public List<String> deletedMessagesID = new ArrayList<String>();

    public boolean deletable = false, deleted = true;
    public List<Member> team1 = new ArrayList<Member>();
    public List<Member> team2 = new ArrayList<Member>();

    public Member initialiser;
    public TextChannel channel;
    public String option1 = "", option2 = "";

    public EmbedBuilder embedBuilder = new EmbedBuilder();

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentDisplay().split(" ");
        if (args[0].equalsIgnoreCase("+umfrage")) {
            channel.deleteMessageById(event.getMessageId()).queue();
            if (deleted) {
                initialiser = event.getMember();
                channel = event.getTextChannel();

                boolean arg1 = true;
                for (int i = 0; i < args.length; i++) {
                    if (args[i].startsWith("1=")) {
                        option1 = args[i] + " ";
                    } else if (args[i].startsWith("2=")) {
                        arg1 = false;
                        option2 = args[i];
                    } else {
                        if (arg1) {
                            option1 += args[i] + " ";
                        } else {
                            option2 += args[i] + " ";
                        }
                    }
                }
                option1 = option1.replace("1=", "");
                option2 = option2.replace("2=", "");

                embedBuilder.clear();
                embedBuilder.setTitle("Umfrage");
                embedBuilder.setDescription("Eine kleine Umfrage von " + initialiser.getEffectiveName());
                embedBuilder.setColor(Color.GREEN);

                if (!option1.isBlank() && !option2.isBlank()) {

                    embedBuilder.addField("Was ist besser?", "Ihr seid jetzt dran! Was findet ihr besser?", false);
                    embedBuilder.addField("Option :one:", option1, true);
                    embedBuilder.addField("oder", "", true);
                    embedBuilder.addField("Option :two:", option2, true);

                    event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                        message.addReaction("U+0031 U+20E3").queue();
                        message.addReaction("U+25FC").queue();
                        message.addReaction("U+0032 U+20E3").queue();
                        message.addReaction("U+2B1B").queue();
                        message.addReaction("U+1F512").queue();
                    });
                    deleted = false;
                } else {
                    embedBuilder.clear();
                    embedBuilder.addField("Fehler", "Ohje, leider hat " + initialiser.getEffectiveName() + " den Command falsch verwendet", false);
                    embedBuilder.addField("Korrekte Verwendung", "+umfrage 1=bla 2=blabla", false);
                    embedBuilder.setFooter("Optionen dürfen Leerzeichen enthalten");
                    embedBuilder.setColor(Color.RED);

                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                }
            } else {
                embedBuilder.clear();
                embedBuilder.addField("Fehler", "Ohje, leider ist bereits eine Umfrage aktiv", false);
                embedBuilder.addField("Behebung", "Schließe die vorherige Umfrage mit :lock: und bestätige es mit :heavy_check_mark:", false);
                embedBuilder.setColor(Color.RED);
                embedBuilder.setFooter("Optionen dürfen Leerzeichen enthalten");
                event.getChannel().sendMessage(embedBuilder.build()).queue();
            }
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent ReactionAddEvent) {
        if (!ReactionAddEvent.getUser().isBot() && !deletedMessagesID.contains(ReactionAddEvent.getMessageId())) {
            if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+25fc") || ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+2b1b")) {
                ReactionAddEvent.getReaction().removeReaction(ReactionAddEvent.getUser()).queue();
            } else if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+31U+20e3")) {
                System.out.println(ReactionAddEvent.getMember() + " reacted with Number1");
                if (team2.contains(ReactionAddEvent.getMember())) {
                    ReactionAddEvent.getReaction().removeReaction(ReactionAddEvent.getUser()).queue();
                    System.out.println(ReactionAddEvent.getMember() + " is already in Team2");
                } else {
                    team1.add(ReactionAddEvent.getMember());
                    System.out.println(ReactionAddEvent.getMember() + " added to Team1");
                }
                updateStats(ReactionAddEvent.getMessageId());
            } else if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+32U+20e3")) {
                System.out.println(ReactionAddEvent.getMember() + " reacted with Number2");
                if (team1.contains(ReactionAddEvent.getMember())) {
                    ReactionAddEvent.getReaction().removeReaction(ReactionAddEvent.getUser()).queue();
                    System.out.println(ReactionAddEvent.getMember() + " is already in Team1");
                } else {
                    team2.add(ReactionAddEvent.getMember());
                    System.out.println(ReactionAddEvent.getMember() + " added to Team2");
                }
                try {
                    updateStats(ReactionAddEvent.getMessageId());
                } catch (NumberFormatException e) {
                }
            } else if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+1f512")) {
                ReactionAddEvent.getChannel().addReactionById(ReactionAddEvent.getMessageId(), "U+2714").queue();
                deletable = true;
            } else if (ReactionAddEvent.getReactionEmote().getAsCodepoints().equals("U+2714") && deletable) {
                ReactionAddEvent.getChannel().clearReactionsById(ReactionAddEvent.getMessageId()).queue();

                deletedMessagesID.add(ReactionAddEvent.getMessageId());
                team1.clear();
                team2.clear();
                deleted = true;
            }
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent ReactionRemoveEvent) {
        if (!ReactionRemoveEvent.getUser().isBot()) {
            if (ReactionRemoveEvent.getReactionEmote().getAsCodepoints().equals("U+31U+20e3")) {
                team1.remove(ReactionRemoveEvent.getMember());
                System.out.println(ReactionRemoveEvent.getMember() + " removed from Team1");
                try {
                    updateStats(ReactionRemoveEvent.getMessageId());
                } catch (NumberFormatException e) {
                }
            } else if (ReactionRemoveEvent.getReactionEmote().getAsCodepoints().equals("U+32U+20e3")) {
                team2.remove(ReactionRemoveEvent.getMember());
                System.out.println(ReactionRemoveEvent.getMember() + " removed from Team2");
                try {
                    updateStats(ReactionRemoveEvent.getMessageId());
                } catch (NumberFormatException e) {
                }
            } else if (ReactionRemoveEvent.getReactionEmote().getAsCodepoints().equals("U+1f512")) {
                ReactionRemoveEvent.getChannel().removeReactionById(ReactionRemoveEvent.getMessageId(), "U+2714").queue();
                deletable = false;
            }
        }
    }

    public void updateStats(String messageID) {
        embedBuilder.clear();
        embedBuilder.setTitle("Umfrage");
        embedBuilder.setDescription("Eine kleine Umfrage von " + initialiser.getEffectiveName());
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.addField("Was ist besser?", "Ihr seid jetzt dran! Was findet ihr besser?", false);
        embedBuilder.addField("Option :one:", option1, true);
        embedBuilder.addField("oder", "", true);
        embedBuilder.addField("Option :two:", option2, true);

        int intTeam1 = team1.size(), intTeam2 = team2.size();
        double overall = intTeam1 + intTeam2;
        double factor = 100 / overall;

        String blocksT1 = Double.toString(intTeam1 * factor).substring(0, 2);
        String blocksT2 = Double.toString(intTeam2 * factor).substring(0, 2);

        int intBlocksT1 = Integer.parseInt(blocksT1.substring(0, 1)), intBlocksT2 = Integer.parseInt(blocksT2.substring(0, 1));
        if (intBlocksT1 + intBlocksT2 != 10) {
            if (blocksT1.charAt(1) > blocksT2.charAt(1)) {
                intBlocksT1 = 10 - intBlocksT2;
            } else if (blocksT1.charAt(1) < blocksT2.charAt(1)) {
                intBlocksT2 = 10 - intBlocksT1;
            }
        }
        String String_blocksTeam1 = "", String_blocksTeam2 = "";
        for (int i = intBlocksT1; i >= 1; i--) {
            String_blocksTeam1 += ":blue_square:";
        }
        for (int j = intBlocksT2; j >= 1; j--) {
            String_blocksTeam2 += ":red_square:";
        }

        embedBuilder.addField("Ergebnis", String_blocksTeam1 + "|" + String_blocksTeam2, false);
        channel.editMessageById(messageID, embedBuilder.build()).queue();
    }
}
