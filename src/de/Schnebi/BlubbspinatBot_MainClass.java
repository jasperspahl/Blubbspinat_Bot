package de.Schnebi;

import de.Schnebi.Listeners.GiveawayListener;
import de.Schnebi.Listeners.SchnebiLogger;
import de.Schnebi.Listeners.SuperListenerv1;
import de.Schnebi.Listeners.TicTacToeListener;
import de.Schnebi.Listeners.UmfrageListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class BlubbspinatBot_MainClass {

    public ShardManager shardMan;

    public static void main(String[] args) {

        try {
            new BlubbspinatBot_MainClass();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public BlubbspinatBot_MainClass() throws LoginException, IllegalArgumentException {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("ODE5MTUyNTAyMjIyMDk0MzY2.YEidNw.BFlYeJplN3leERdC8nl6e37Zqoo");

        builder.setActivity(Activity.watching("dass der Blubbspinat nicht überkocht"));
        builder.setStatus(OnlineStatus.ONLINE);

        builder.addEventListeners(new SchnebiLogger());
        builder.addEventListeners(new TicTacToeListener());
        builder.addEventListeners(new GiveawayListener());
        builder.addEventListeners(new UmfrageListener());

        builder.addEventListeners(new SuperListenerv1());

        this.shardMan = builder.build();
        CheckForShutdown();

    }

    public void CheckForShutdown() {

        new Thread(() -> {

            String line;
            BufferedReader Bfrdreader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while ((line = Bfrdreader.readLine()) != null) {
                    if (line.equalsIgnoreCase("exit")) {
                        if (shardMan != null) {
                            shardMan.setStatus(OnlineStatus.OFFLINE);
                            shardMan.shutdown();
                            System.out.println("Bot offline");
                        }

                        Bfrdreader.close();
                        System.exit(0);

                    } else {
                        System.out.println("Use 'exit' for shutdown.");
                    }

                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }).start();

    }

}
