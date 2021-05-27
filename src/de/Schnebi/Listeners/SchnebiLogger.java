package de.Schnebi.Listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author jansc
 */
public class SchnebiLogger extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.TEXT) && !(event.getAuthor().isBot()) && !(event.getMessage().getContentDisplay() == null) && event.getMessage().getAttachments().size() == 0) {
            if (!event.getMessage().getContentDisplay().startsWith("+")) {

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                System.out.println(
                        "\nSchnebi Logger: Received Message!" +
                        "\nTime:    " + sdf.format(cal.getTime()) +
                        "\nServer:  " + event.getGuild() +
                        "\nChannel: " + event.getChannel() +
                        "\nSender:  " + event.getAuthor() +
                        "\nContent Start: \n" + event.getMessage().getContentDisplay() +
                        "\nContent End\n"
                );

                /* Output Template
                * Server:  [Type (G = Guild)] [Name] [ID]
                * Channel: [Type (TC = TextChannel)] [Name] [ID]
                * Sender:  [Type (U = User)] [Name] [ID]
                * Content: [Inhalt der Nachricht]
                *
                * Output sieht so aus
                * Schnebi Logger: Received Message!
                * Server: G:Testserver für DiscordBumper(814186682344669216)
                * Channel: TC:test(814435470460452895)
                * Sender: U:Jan der Schnebi(264396390199721984)
                * Content: Kleiner Test
                 */
            }
        }
    }
}
