package model;

/**
 * Created by Mobile App Develop on 7-1-16.
 */
public class ProfileModel {
    private String profileName;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String height;
    private String weight;
    private String email;
    private String contactNo;
    private String personId; //Using National ID or Birth Certificate
    private String idType;
    private String imagePath;
    private int id;
    private String majorDisease;
    private String nid_bcn;
    private String relation;
    private String profile_flag;


    public int getId() {
        return id;
    }

    public ProfileModel(String profileName, String dateOfBirth, String gender, String bloodGroup, String height, String weight, String email, String contactNo, String personId, String idType,
                        String imagePath,String majorDisease, String nid_bcn, String relation, String profile_flag) {
        this.profileName = profileName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.contactNo = contactNo;
        this.personId = personId;
        this.idType = idType;
        this.imagePath = imagePath;
        this.majorDisease = majorDisease;
        this.nid_bcn = nid_bcn;
        this.relation = relation;
        this.profile_flag = profile_flag;

    }


    public ProfileModel(int id,String profileName, String dateOfBirth, String gender, String bloodGroup, String height, String weight,
                        String email, String contactNo, String personId, String idType, String imagePath,String majorDisease,
                        String nid_bcn, String relation, String profile_flag) {

        this.id = id;
        this.profileName = profileName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.contactNo = contactNo;
        this.personId = personId;
        this.idType = idType;
        this.imagePath = imagePath;
        this.majorDisease = majorDisease;
        this.nid_bcn = nid_bcn;
        this.relation = relation;
        this.profile_flag = profile_flag;

    }

    public ProfileModel() {
    }

    public String getMajorDisease() {
        return majorDisease;
    }

    public void setMajorDisease(String majorDisease) {
        this.majorDisease = majorDisease;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getNid_bcn() {
        return nid_bcn;
    }

    public void setNid_bcn(String nid_bcn) {
        this.nid_bcn = nid_bcn;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }


    public String getProfile_flag() {
        return profile_flag;
    }

    public void setProfile_flag(String profile_flag) {
        this.profile_flag = profile_flag;
    }
}
