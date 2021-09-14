/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.Schnebi.Listeners;

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

    String prefix = "+";
    String[] args;
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
                eventMember = event.getMember();
                message = event.getMessage();
                Channel = event.getTextChannel();

            }
        }
    }
}
