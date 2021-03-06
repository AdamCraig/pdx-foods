
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ReviewTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/pdx_foods_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteReviewsQuery = "DELETE FROM reviews *;";
      String deleteRestaurantsQuery = "DELETE FROM restaurants *;";
      con.createQuery(deleteReviewsQuery).executeUpdate();
      con.createQuery(deleteRestaurantsQuery).executeUpdate();
    }
  }

  @Test
  public void review_instantiatesCorrectly_true() {
    Review myReview = new Review("Just the best!");
    assertEquals(true, myReview instanceof Review);
  }

  @Test
  public void getDescription_reviewInstantiatesWithDescription_String() {
    Review myReview = new Review("Just the best!");
    assertEquals("Just the best!", myReview.getDescription());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Review.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Review firstReview = new Review("Hey it's good");
    Review secondReview = new Review("Hey it's good");
    assertTrue(firstReview.equals(secondReview));
  }

  @Test
  public void save_savesReviewIntoDatabase_true() {
    Review myReview = new Review("I guess it's kinda okay");
    myReview.save();
    assertTrue(Review.all().get(0).equals(myReview));
  }

  @Test
  public void find_findsReviewInDatabase_true() {
    Review myReview = new Review("Amazing!");
    myReview.save();
    Review savedReview = Review.find(myReview.getId());
    assertTrue(myReview.equals(savedReview));
  }
}
