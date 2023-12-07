package nl.avasten.day7;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/day7")
public class Day7 {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSum() {

        List<Integer> times = List.of(59, 68, 82, 74);
        List<Integer> distances = List.of(543, 1020, 1664, 1022);

        long totalWinnings = 0L;
        {
            int q = 0;
            for (int i : times) {
                if (q == 0) {
                    totalWinnings = calcOptions(i, distances.get(q));
                } else {
                    totalWinnings = totalWinnings * calcOptions(i, distances.get(q));
                }
                q++;
            }
        }

        return "Sum" + totalWinnings;
    }

    public int calcOptions(Integer miliseconds, Integer currentRecord) {

        int options = 0;
        int hold = 1;

        for (int i = miliseconds; i >= 1; i--) {
            int going = miliseconds - hold;
            int speed = hold;
            int length = going * speed;

            if (length > currentRecord) {
                options++;
            }

            System.out.println("Hold seconds: " + hold + " , going seconds: " + going + " , length: " + length);

            hold++;
        }

        return options;
    }
}
