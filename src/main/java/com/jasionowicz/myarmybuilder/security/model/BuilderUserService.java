package com.jasionowicz.myarmybuilder.security.model;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class BuilderUserService implements UserDetailsService {

    @Autowired
    private BuilderUserRepository builderUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BuilderUser> user = builderUserRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String getRoles(BuilderUser user) {
        if (user.getRole() == null) {
            return "USER";
        }

        return user.getRole();
    }

    public List<ArmyComposition> getArmyTemplateById(int userId) {
        BuilderUser builderUser = builderUserRepository.getReferenceById(userId);
        List<ArmyComposition> armyCompositionList = builderUser.getArmyCompositions();

        return armyCompositionList;
    }

    public void isUserLoggedIn(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            BuilderUser builderUser = (BuilderUser) authentication.getPrincipal();
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", builderUser.getUsername());
        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }


    public Optional<User> getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        System.out.println(principal.getClass());
        if (!(principal instanceof User)) {
            return Optional.empty();
        }
        return Optional.of((User) principal);
    }

    public Optional<BuilderUser> getLoggedBuilder() {
       return getLoggedUser().map(user -> user.getBuilderUser());
    }





}


//BuilderUser (security)
//ArmyCompositor (budowanie armii)