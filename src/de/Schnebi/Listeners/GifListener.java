/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class GifListener extends ListenerAdapter {

    public EmbedBuilder embedBuilder = new EmbedBuilder();
    
    public String registredGifs = "```\nrawr\nyallah\negal\nohgod```";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws IndexOutOfBoundsException {
        
        embedBuilder.clear();
        embedBuilder.setTitle("Blubbspinat Bot");
        embedBuilder.setFooter("+blubb");
        embedBuilder.setColor(Color.GREEN);

        if (event.isFromType(ChannelType.TEXT) && event.getMessage().getAttachments().isEmpty() && event.getMessage().getEmotes().isEmpty()) {
            if (!(event.getAuthor().isBot())) {
                
                TextChannel channel = event.getTextChannel();
                try {
                String prefix = event.getMessage().getContentDisplay().substring(0, 4);
                
                

                if (prefix.equalsIgnoreCase("+gif") && event.getMessage().getContentDisplay().length() >= 5) {
                    channel.deleteMessageById(event.getMessageId()).queue();
                    String[] args = event.getMessage().getContentDisplay().substring(5).split(" ");
                    if (args.length == 1) {
                    String GifLink = "default";
                    
                    switch (args[0]) {
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
                            break;
                        }
                    
                        if (!(GifLink.equals("default"))) {
                            channel.sendMessage(GifLink).queue();
                        } else {
                            channel.sendMessage(embedBuilder.build()).queue();
                        }
                    }
                    }
                } catch (StringIndexOutOfBoundsException e) {}
            }
        }
    }
}
