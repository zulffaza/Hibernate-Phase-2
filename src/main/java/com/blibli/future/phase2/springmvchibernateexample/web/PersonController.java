package com.blibli.future.phase2.springmvchibernateexample.web;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.blibli.future.phase2.springmvchibernateexample.model.Person;

@Controller
public class PersonController {

    private static final Logger logger = Logger.getLogger(PersonController.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/addPerson.htm", method = RequestMethod.POST)
    public ModelAndView addPerson(Person p, Errors errors) {
        Session session = sessionFactory.openSession();

        logger.info(p.getName());
        System.out.println(p.getName());

        if (errors.hasErrors()) {
            ModelAndView mav = new ModelAndView("addPerson");
            mav.addObject("errors", errors);
            return mav;
        }

        try {
            session.save(p);
        } catch (Exception e) {
            logger.info(e.getMessage());
            System.out.println(e.getMessage());
        }

        session.close();
        return new ModelAndView("redirect:/allPersons.htm");
    }

    @RequestMapping(value = "/addPerson.htm")
    public String addPerson() {
        return "addPerson";
    }

    @RequestMapping("/allPersons.htm")
    public ModelAndView allPersons() {
        Session session = sessionFactory.openSession();
        List<Person> persons = session.createQuery("FROM Person").list();

        session.close();

        ModelAndView mav = new ModelAndView("allPersons");
        mav.addObject("persons", persons);
        return mav;
    }
}
