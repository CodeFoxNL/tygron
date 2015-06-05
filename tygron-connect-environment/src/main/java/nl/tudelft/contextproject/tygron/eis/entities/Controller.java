package nl.tudelft.contextproject.tygron.eis.entities;

import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.eis.TygronPercept;

import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.translation.Filter;

import java.util.List;

public class Controller {
  private IndicatorEntity indicators;
  private StakeholderEntity stakeholders;
  private EconomyEntity economies;
  private Environment env;

  /**
   * Controller constructor.
   * 
   * @param controller
   *          the session object
   */
  public Controller(Environment controller) {
    indicators = new IndicatorEntity(controller.loadIndicators());
    stakeholders = new StakeholderEntity(controller.loadStakeholders());
    economies = new EconomyEntity(controller.loadEconomies());
    env = controller;
  }

  /**
   * Percept the stakeholders from the environment.
   * 
   * @return the stakeholders
   */
  @AsPercept(name = "stakeholder", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ONCE)
  public List<TygronPercept> stakeholder() {
    return stakeholders.stakeholder();
  }

  /**
   * Percepts the indicators on change.
   * 
   * @return the list of indicators
   */
  @AsPercept(name = "indicator", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> progressIndicator() {
    return indicators.progressIndicator();
  }

  /**
   * Percepts the initIndicators once.
   * 
   * @return the list of indicators
   */
  @AsPercept(name = "initIndicator", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ONCE)
  public List<TygronPercept> initIndicator() {
    return stakeholders.initIndicator();
  }

  /**
   * Percepts the initIndicators on change.
   * 
   * @return the list of indicators
   */
  @AsPercept(name = "economy", multiplePercepts = true, multipleArguments = true, filter = Filter.Type.ON_CHANGE)
  public List<TygronPercept> economies() {
    return economies.economies();
  }
  
  /**
   * Build action.
   * @param surface the surface to build
   * @param type the type of the building
   */
  @AsAction(name = "build")
  public void build(int surface, int type){
    env.build(surface, type);
  }
  
  /**
   * Buy land action.
   * @param surface the surface to buy
   * @param cost the price to buy it
   */
  @AsAction(name = "buyLand")
  public void buyLand(int surface, int cost){
    env.buyLand(surface, cost);
  }

}