/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
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
public class SuperListenerV2 extends ListenerAdapter {

    String prefix = "+", messageContent, gifLink;
    String[] args;
    boolean toggled_spaces = false, send_on_final;

    Member eventMember;
    Message message;
    TextChannel Channel;
    EmbedBuilder embedBuilder = new EmbedBuilder();

    //strg+c & strg+v
    String commands = "```prefix: Gibt den prefix an, in diesem Fall '" + prefix + "'\nblubb: Listet unsere Commands auf.\nhallo: blubb!\nwerist @User: Gibt informationen über den User wieder"
            + "\ncoinflip/münzwurf: Wirft eine Münze\ndice/würfel: Würfelt eine zufüllige Zahl\ngarticphone/gp: Link für Gartic Phone\ncardsagainsthumanity/cah: Link für Cards Against Humanity"
            + "\nCodenames/cn: Link für Codenames\nquote: Zitiert dich selbst\nstats: Zeigt die Statistik eines Nutzers an\nvaried: nA wAs MaChT dAs WoHl?\nohrwurm: Drei Chinesen mit dem Kontrabass\ngiveaway: Erlaubt es dir etwas an andere zu verschenken"
            + "\numfrage: Erlaubt es dir eine Umfrage aus zwei Optionen zu erstellen\ntoggle space(s): (De)Aktiviert die Leerzeichenersetzung```";
    String registredGifs = "```\nrawr\nyallah\negal\nohgod```";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && event.getMessage().getAttachments().size() == 0) {
            if (!(event.getAuthor().isBot())) {
                embedBuilder.clearFields();
                if (toggled_spaces || event.getMessage().getContentDisplay().contains("  ")) {
                    String content = event.getMessage().getContentDisplay();
                    int counter = 0;
                    do {
                        content = content.replaceFirst("  ", " ");
                        counter++;
                    } while (content.contains("  "));
                    embedBuilder.addField(event.getMember().getEffectiveName() + " wollte:", content + "\n\n sagen und nutze dabei `" + counter + "` Leerzeichen zu viel :man_facepalming:.", false);
                    Channel.sendMessage(embedBuilder.build()).queue();
                } else {
                    eventMember = event.getMember();
                    message = event.getMessage();
                    Channel = event.getTextChannel();

                    embedBuilder.clear();
                    embedBuilder.setTitle("Blubbspinat Bot");
                    embedBuilder.setFooter("+blubb");
                    embedBuilder.setColor(Color.GREEN);

                    messageContent = message.getContentStripped();

                    if (messageContent.equalsIgnoreCase("prefix")) {
                        embedBuilder.addField("Prefix", "Unser prefix ist '" + prefix + "'", false);
                        System.out.println("prefix");
                        Channel.deleteMessageById(message.getId()).queue();
                        Channel.sendMessage(embedBuilder.build()).queue();
                    }

                    if (messageContent.startsWith(prefix)) {
                        args = messageContent.substring(1).split(" ");
                        handleCommands(event, args);
                    }
                }
            }
        }
    }
    
    
    
    void handleCommands(MessageReceivedEvent event, String[] command) {
        System.out.println("handling commands");
        send_on_final = true;
        if (command.length >= 2) {
            if (command[0].equals("varied")) {
                String Smessage = messageContent.substring(8);
                String[] everyChar = Smessage.split("");
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
            } else if (command[0].equals("quote")) {
                embedBuilder.addField("", messageContent.substring(7), false);
                embedBuilder.addField("~" + eventMember.getEffectiveName(), "", false);
            }
        }
        
        if (command.length == 2) {
            switch (command[0]) {
                    case "dice":
                    case "würfel":
                        luckHandler(event, "customdice");
                        break;
                    case "toggle":
                        switch (command[1]) {
                            case "space":
                            case "spaces":
                                toggled_spaces = !toggled_spaces;
                                embedBuilder.addField("Leerzeichenersetzung", "Du hast die Leerzeichenersetzung nun auf `" + toggled_spaces + "` gesetzt", false);
                                break;
                        }
                        break;
                    case "delete":
                        int amount = Integer.parseInt(command[1]);
                        if (3 <= amount && amount <= 51) {
                            List<Message> messages = Channel.getHistory().retrievePast(amount).complete();
                            messages.remove(0);
                            Channel.deleteMessages(messages).complete();
                            embedBuilder.addField("Gelöschte Nachrichten", "Ich habe erfolgreich " + amount + " Nachrichten gelöscht", false);
                        } else {
                            embedBuilder.addField("Fehler: Anzahl", "Mögliche Werte für diesen Command sind ```3-51```", false);
                        }
                        break;
                    case "gif":
                    case "gifs":
                        gifHandler(event, command[1]);
                        break;
                }
        }
        if (embedBuilder.getFields().isEmpty()) {
            embedBuilder.addField(":thinking: hmmm", "Ups, da lief was schief. Ich kenne diesen Command nicht", false);
        }
        
        Channel.deleteMessageById(message.getId()).queue();
        if (send_on_final) {
            Channel.sendMessage(embedBuilder.build()).queue();
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
                int randomNumer = random.nextInt(Integer.parseInt(args[1])) + 1;
                embedBuilder.addField("Würfelwurf von 1-" + Integer.parseInt(args[1]) + ":", Integer.toString(randomNumer), false);
                break;
        }
    }
    
    void gifHandler(MessageReceivedEvent event, String gifName) {
        gifLink = "default";
        send_on_final = false;
        switch(gifName) {
            case "help":
                embedBuilder.addField("Gif Manager", "Unsere registrierten Commands für Gifs sind: \n" + registredGifs, false);
                break;
            case "rawr":
                gifLink = "https://tenor.com/view/satan-kitty-strangers-devil-candy-gif-16092089";
                break;
            case "yallah":
                gifLink = "https://tenor.com/view/yallah-dwayne-johnson-pointing-gif-14679728";
                break;
            case "egal":
                gifLink = "https://tenor.com/view/egal-singing-ocean-sea-boat-ride-gif-16080257";
                break;
            case "ohgod":
                gifLink = "https://tenor.com/view/shame-regret-panda-ohgod-whathaveidone-gif-4520297";
            default:
                embedBuilder.addField("Gif Manager", "Dieser Command ist mit keinem Gif verknüpft. Für eine Auflistung unserer Gifs nutze +gif help", false);
                send_on_final = true;
        }
        if (!send_on_final) {
            Channel.sendMessage(gifLink).queue();
        }
    }
}
