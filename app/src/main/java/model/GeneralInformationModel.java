package model;

import java.util.ArrayList;

/**
 * Created by Rubayet on 20-Jan-16.
 */
public class GeneralInformationModel {
    private String name;
    private String description;
    private static ArrayList<GeneralInformationModel> al;
    public GeneralInformationModel(String name,String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public static ArrayList<GeneralInformationModel> getAllData(){
        al = new ArrayList<GeneralInformationModel>();
        al.add(new GeneralInformationModel("1.Drinking Water:","Maximum activities of human body are dependent on water for their proper functions. We should drink a gallon of water every day."));
        al.add(new GeneralInformationModel("2.Proper Exercises:","For a hygienic body daily or interval base exercises are necessary which keep body fit."));
        al.add(new GeneralInformationModel("3.Use of Balanced Diet:","It is recommended to use such a diet which contains all proper ingredients like protein, carbohydrates, vitamins and iron"));
        al.add(new GeneralInformationModel("4.Proper Sleeping:","A suitable sleeping is the most important thing for a fit body and its absence may cause a lot of disorder and even severe disease."));
        al.add(new GeneralInformationModel("5.Taking Rest:","All human body organs needed rest for their normal functions human body is like a machine and if this machine is over worked is may lose its balance."));
        al.add(new GeneralInformationModel("6.Entertaining Activities:","For proper work human brain and body need aesthetic activities which make your mind work properly that is control center of whole human body."));
        al.add(new GeneralInformationModel("7.Participating in Sports:","In-door and outdoor games are key for a successful life. Games which demand mental exertion and physical energy are important for good health."));
        al.add(new GeneralInformationModel("8.Cleanliness:","Cleanliness is the part of most civilization’s moral values as well as part of world religions."));
        al.add(new GeneralInformationModel("9.Following a schedule:","Keeping to a regular schedule has both physical and mental benefits. So it is suggested to follow a proper schedule."));
        al.add(new GeneralInformationModel("10.Quit Smoking:","Smoking can cause lung disease by damaging your airways and the small air sacs (alveoli) found in your lungs. "));
        al.add(new GeneralInformationModel("11.Take care of your skin:","Always wear sun-screen lotion during summers. It is advisable to use winter care creams to overcome the harsh and cold winds."));
        al.add(new GeneralInformationModel("12.Slow down on the junk:","Research shows that eating too many high-fat-food contributes to high blood-cholesterol levels, which can cause hardening of the arteries, coronary heart disease and stroke."));
        al.add(new GeneralInformationModel("13.Coffee is good:","Researchers have found that two to four cups of coffee daily can lower the risk of colon cancer by 25 per cent."));
        al.add(new GeneralInformationModel("14.Lower your cholesterol:","Work on reducing your cholesterol. This can reduce the risk of heart attack and stroke even when your level is not high. Exercise to reduce weight."));
        al.add(new GeneralInformationModel("15.Socialising is good:"," Meeting friends and relatives is recommended. Weekly socialising improves thememory, concentration and problem solving skills."));
        al.add(new GeneralInformationModel("16.Fruits and vegetables help:","Have at least five portions of vegetables and fruit a day, especially tomatoes, red grapes."));
        al.add(new GeneralInformationModel("17.Vitamins are vital:","A multivit a day keeps the tablet away, but be sure it contains at least 200 meg of folic acid."));
        al.add(new GeneralInformationModel("18.Being overweight is dangerous:","Loose the extra kilos. Over weight people cut 20 weeks of their life for every excess kilogram, according to new research.Keeping a personal weight machine at home really helps. Buy one now!"));
        al.add(new GeneralInformationModel("19.Supplement with selenium:"," Research has shown that people who took a daily supplement of selenium had a 37 per cent reduction in cancers."));
        al.add(new GeneralInformationModel("20.Change your job:","If the workplace is what bothers you. Simply quit! Consider becoming a salesperson. Salespeople are least likely to have a work-related illness."));
        return al;
    }
}
