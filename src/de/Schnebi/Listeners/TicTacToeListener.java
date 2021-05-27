/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

import java.awt.Color;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class TicTacToeListener extends ListenerAdapter {

    public static List<Member> Duelists;
    public static EmbedBuilder embedBuilder = new EmbedBuilder();
    public static String[] positions = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    
    public static String Field = "";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws IndexOutOfBoundsException {

        if (event.isFromType(ChannelType.TEXT) && event.getMessage().getAttachments().size() == 0) {
            if (!(event.getAuthor().isBot())) {
                if (event.getMessage().getContentDisplay().startsWith("+tictactoe")) {
                    embedBuilder.clear();
                    embedBuilder.setTitle("TicTacToe");
                    embedBuilder.setFooter("+blubb");
                    embedBuilder.setColor(Color.GREEN);

                    Duelists.add(event.getMember());
                    Duelists.add(event.getMessage().getMentionedMembers().get(0));

                    embedBuilder.addField("TicTacToe | Spielbrett", Field , false);
                    
                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                }

            }
        }
    }
}
