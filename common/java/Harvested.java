
package common.java.Harvested;


import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.function.Predicate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public Harvested(int id, String type, String name, String area, String planet, String size, String food){

    this.id = id;
    this.type = type;
    this.name = name;
    this.area = area;
    this.planet = planet;
    this.size = size;
    this.food = food;
}

public int getId() {
    return id;
}

public void setId(int id){
    this.id = id;
}

public String getType(){
    return type;
}

public void setType(String type){
    this.type = type;
}

public String getName(){
    return name;
}

public void setName(String name){
    this.name = name;
}

public String getArea(){
    return area;
}

public void setArea(String area){
    this.area = area;
}

public String getPlanet(){
    return planet;

}

public void setPlanet(String planet){
    this.planet = planet;
}

public String getSize(){
    return size;
}

public void setSize(String size){
    this.size = size;
}

public String getFood(){
    return food;
}

public void setFood(String food){
    this.food = food;
}
public class HarvestedDaoDbImpl implements HarvestedDao {

    private static final String SQL_INSERT_HARVESTED 
        = "INSERT INTO Harvested (hType, hName, hArea, hPlanet, hSize, hFood) 
        " + " VALUES(?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_DELETE_HARVESTED 
        = "DELETE FROM Harvested WHERE id = ?";  
        
    private static final String SQL_SELECT_HARVESTED_BY_ID
        = "SELECT * FROM Harvested WHERE id = ?";    

    private static final String SQL_SELECT_ALL_HARVESTED
        = "SELECT * FROM Harvested";

    private static final String SQL_UPDATE_HARVESTED
        = "UPDATE Harvested SET hType = ?, hName = ?, hArea = ?, hPlanet = ?
        hSize = ?, hFood = ?";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }
}

@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public Harvested addHarvested(Harvested harvested) {
    jdbcTemplate.update(SQL_INSERT_HARVESTED, 
            harvested.getType(),
            harvested.getName(),
            harvested.getArea(),
            harvested.getPlanet(),
            harvested.getSize(),
            harvested.getFood());

}

@Override
public Harvested getHarvestedById(int id){
    try{
        jdbcTemplate.queryForObject(SQL_SELECT_HARVESTED_BY_ID,
            new HarvestedMapper(), id);
    } catch (EmptyResultDataAccessException exception){
        return null;
    }
}
@Override 
public List<Harvested> getAllHarvested(){
    return jdbcTemplate.query(SQL_SELECT_ALL_HARVESTED, new HarvestedMapper());
}

@Override
public void updateHarvested(Harvested harvested) {
    jdbcTemplate.update(SQL_UPDATE_HARVESTED, 
            harvested.getType(),
            harvested.getName(),
            harvested.getArea(),
            harvested.getPlanet(),
            harvested.getSize(),
            harvested.getFood());

} 

@Override
public void removeHarvested(int id){
    jdbcTemplate.update(SQL_DELETE_HARVESTED, id);
}

@Override
public Harvested maprow(ResultSet rs, int rowNum) throws SQLException {
    Harvested harvested = new Harvested();
    harvested.setId(rs.getInt("id"));
    harvested.setType(rs.getString("type"));
    harvested.setName(rs.getString("name"));
    harvested.setArea(rs.getString("area"));
    harvested.setPlanet(rs.getString("planet"));
    harvested.setSize(rs.getString("size"));
    harvested.setFood(rs.getString("food"));

    return harvested;
}
// public interface HarvestedDao {

//     public Harvested addHarvested(Harvested harvested);

//     public Harvested getHarvestedByName(String name);

//     public List<Harvested> getAllHarvested();

//     public void updateHarvested(Harvested harvested);

//     public void removeHarvested(String name);

    

// }

//THE CONTROLLER FOR METHODS AND STUFF

@Controller
public class Controller {
    private HarvestedDao dao;

    @Inject
    public Controller(HarvestedDao dao){
        this.dao = dao;

    }

    @RequestMapping(value= {"/", "/index"}, method = RequestMethod.GET)
    public String displayPage(){

        return "index";
    }
//This is for creating a new harvested object.
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/harvested", method = RequestBody.POST)
    public Harvested createHarvested(@Valid @RequestBody Harvested incomingHarvested){
        dao.addHarvested(incomingHarvested);
        return incomingHarvested;

    }
//FOR GETTING ALL OF THE HARVESTED OBJECTS
    @ResponseBody
    @RequestMapping(value = "/harvests", method = RequestMethod.GET)
    public List<Harvested> getAllHarvested(){
        return dao.getAllHarvested();
    }
//FOR UPDATING AN ALREADY EXISTING HARVESTED OBJECT
    @ResponseBody
    @RequestMapping(value = "harvested{id}", method = RequestMethod.GET)
    public Harvested updateHarvested(@PathVariable("id") int harvestedId, @RequestBody Harvested updatedHarvested){
        updatedHarvested.setId(harvestedId);
        dao.updatedHarvested(updatedHarvested);
    }
//FOR DELETING AN ALREADY EXISTING HARVESTED OBJECT
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/harvested{id}", method = RequestMethod.DELETE)
    public void removeHarvested(@PathVariable("id") int harvestedId){
        dao.removeHarvested(harvestedId);

    }
}