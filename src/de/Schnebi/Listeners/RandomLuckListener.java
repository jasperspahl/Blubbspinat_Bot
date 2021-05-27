/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import java.util.Random;
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
public class RandomLuckListener extends ListenerAdapter {

    public String[] Outcome = {
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf",
        "Kopf", //50 Prozent
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",
        "Zahl",//50 Prozent
    };
    public String BannedUsers = " ";
    public EmbedBuilder embedBuilder = new EmbedBuilder();

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
                        embedBuilder.clear();
                        embedBuilder.setTitle("Blubbspinat Bot");
                        embedBuilder.setFooter("+blubb");
                        embedBuilder.setColor(Color.GREEN);

                        if (args[0].equalsIgnoreCase("coinflip") || args[0].equalsIgnoreCase("münzwurf")) {
                            System.out.println("\n\nConFlipListener: received Message 'coinflip' || 'münzwurf'");
                            Random random = new Random();
                            int randomNumer = random.nextInt(Outcome.length);

                            embedBuilder.addField("Ergebnis des Münzwurfs:", "**" + Outcome[randomNumer] + "**", false);
                            System.out.println("ConFlipListener: Coinflip done, Outcome: " + Outcome[randomNumer]);

                            channel.sendMessage(embedBuilder.build()).queue();
                        } else if (args[0].equalsIgnoreCase("würfel") || args[0].equalsIgnoreCase("dice")) {
                            if (args.length == 1) {
                                Random random = new Random();
                                int randomNumer = random.nextInt(6) + 1;
                                embedBuilder.addField("Würfelwurf:", Integer.toString(randomNumer), false);
                            } else if (args.length == 2) {
                                Random random = new Random();
                                int randomNumer = random.nextInt(Integer.parseInt(args[1]));
                                embedBuilder.addField("Würfelwurf:", Integer.toString(randomNumer), false);
                            }
                            channel.sendMessage(embedBuilder.build()).queue();

//                        } else if (args[0].equalsIgnoreCase("multiwürfel") || args[0].equalsIgnoreCase("dices")) {
//                            if (args.length == 2) {
//                                if (args[1].contains(("[a-zA-Z]+"))) {
//                                    embedBuilder.addField("Multiwürfelwurf", "Bitte verwende den Command wie folgt: \n``+dices [Zahl]", false);
//                                } else {
//                                    int args1 = Integer.parseInt(args[1]);
//                                    int[] randomNumers = null;
//
//                                    Random random = new Random();
//                                    for (int i = 0; i <= args1; i++) {
//                                        randomNumers[i] = random.nextInt(6);
//                                    }
//
//                                    String allRandomNumbers = null;
//                                    for (int i = 0; i <= randomNumers.length; i++) {
//                                        allRandomNumbers += randomNumers[i];
//                                    }
//
//                                    int allRandomNumbersCombined = 0;
//                                    for (int i = 0; i <= randomNumers.length; i++) {
//                                        allRandomNumbersCombined = allRandomNumbersCombined + randomNumers[i];
//                                    }
//
//                                    embedBuilder.addField("Multiwürfelwurf", "Du hast " + args[2] + " Würfel geworfen.", false);
//                                    embedBuilder.addField("Multiwürfel", "Dein Wurf hat " + allRandomNumbersCombined + " ergeben, deine Würfel waren: " + allRandomNumbers, false);
//
//                                }
//                                channel.sendMessage(embedBuilder.build()).queue();
                        }
                    }
                }
            }
        }
    }
}
