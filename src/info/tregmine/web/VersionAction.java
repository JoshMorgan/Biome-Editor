package info.tregmine.web;

import java.io.PrintWriter;

import org.eclipse.jetty.server.Request;

import org.bukkit.Server;

import org.json.JSONWriter;
import org.json.JSONException;

import info.tregmine.Tregmine;
import info.tregmine.WebHandler;

public class VersionAction implements WebHandler.Action
{
    public static class Factory implements WebHandler.ActionFactory
    {
        public Factory()
        {
        }

        @Override
        public String getName()
        {
            return "/version";
        }

        @Override
        public WebHandler.Action createAction(Request request)
        {
            return new VersionAction();
        }
    }

    private String version;

    public VersionAction()
    {
    }

    @Override
    public void queryGameState(Tregmine tregmine)
    {
        Server server = tregmine.getServer();
        version = server.getName() + " " + server.getVersion();
    }

    @Override
    public void generateResponse(PrintWriter writer)
    throws WebHandler.WebException
    {
        try {
            JSONWriter json = new JSONWriter(writer);
            json.object()
                .key("version").value(version)
                .endObject();

            writer.close();
        }
        catch (JSONException e) {
            throw new WebHandler.WebException(e);
        }
    }
}
