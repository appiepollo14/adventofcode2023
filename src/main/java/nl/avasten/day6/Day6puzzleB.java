package nl.avasten.day6;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/day6b")
public class Day6puzzleB {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculateSum() {

        List<Integer> times = List.of(59688274);
        List<Long> distances = List.of(543102016641022L);

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

    public long calcOptions(int miliseconds, long currentRecord) {

        long options = 0L;
        long hold = 1L;

        for (int i = miliseconds; i >= 1; i--) {
            long going = miliseconds - hold;
            long speed = hold;
            long length = going * speed;

            if (length > currentRecord) {
                options++;
            }

//            System.out.println("Hold seconds: " + hold + " , going seconds: " + going + " , length: " + length);

            hold++;
        }

        return options;
    }
}
