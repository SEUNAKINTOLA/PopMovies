package com.example.akintolaoluwaseun.popmovies.domain;

/**
 * Created by AKINTOLA OLUWASEUN on 8/6/2017.
 */


public class mov {

    private String movieTitle;

    // private List<AllResults> results;

    private final static String BASE_URL = "http://image.tmdb.org/t/p/w185//";
    public String getMovieTitle() {

        return movieTitle;
    }

    public void setMovieTitle(String page) {
        this.movieTitle = page;
    }
    /*


    public List<AllResults> getResults() {
        return results;
    }

    public void setResults(List<AllResults> results) {
        this.results = results;
    }
    public static class AllResults {



        private String movieTitle;
        private double viewerRatings;
        private String overview;
        private String releasedDate;
        private String posterImage;

        public String getMovieTitle() {

            return movieTitle;
        }

        public void setMovieTitle(String original_title) {
            this.movieTitle = original_title;
        }


        public double getViewerRatings() {
            return viewerRatings;
        }

        public void setViewerRatings(double vote_average) {
            this.viewerRatings = vote_average;
        }


        public String getReleasedDate() {

            return releasedDate;
        }

        public void setReleasedDate(String release_date) {
            this.releasedDate = release_date;
        }


        public String getPosterImage() {

            return BASE_URL + posterImage;
        }

        public void setPosterImage(String poster_path) {
            this.posterImage = poster_path;
        }


        public String getOverview() {

            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }


        @Override
        public String toString() {
            return "RecipeBean{" +
                    "movieTitle='" + movieTitle + '\'' +
                    ", viewerRatings='" + viewerRatings + '\'' +
                    ", overview='" + overview + '\'' +
                    ", releasedDate=" + releasedDate +
                    ", servinposterImagegs='" + posterImage + '\'' +
                    '}';
        }



    }
         */
}
