package com.mcbox.pesdk.archive.entity;

import java.io.Serializable;

public class Options implements Serializable {
    public static final String GAME_LANGUAGE_CH = "zh_CN";
    public static final String GAME_LANGUAGE_EN = "en_US";
    public static final String SEX_SUFFIX = "Slim";
    public static final String SKIN_TYPE_Alex = "Standard_Alex";
    public static final String SKIN_TYPE_Custom = "Standard_Custom";
    public static final String SKIN_TYPE_Steve = "Standard_Steve";
    private static final long serialVersionUID = -7249156821462100507L;
    private Integer audio_sound;
    private Integer ctrl_invertmouse;
    private Integer ctrl_islefthanded;
    private Float ctrl_sensitivity;
    private Integer ctrl_usetouchjoypad;
    private Integer ctrl_usetouchscreen;
    private String dev_autoloadlevel;
    private String dev_disablefilesystem;
    private String dev_showchunkmap;
    private Integer feedback_vibration;
    private Integer game_difficulty;
    private String game_flatworldlayers;
    private String game_language;
    private Integer game_lastcustomskin;
    private String game_lastcustomskinnew;
    private String game_limitworldsize;
    private Integer game_skintype;
    private String game_skintypefull;
    private Integer game_thirdperson;
    private Integer gfx_animatetextures;
    private Float gfx_dpadscale;
    private Integer gfx_fancygraphics;
    private Integer gfx_fancyskies;
    private String gfx_gamma;
    private Integer gfx_guiscale;
    private Integer gfx_hidegui;
    private Float gfx_pixeldensity;
    private Integer gfx_renderdistance;
    private String gfx_renderdistance_new;
    private Integer mp_server_visible;
    private String mp_username;
    private Integer old_game_version_beta;
    private Integer old_game_version_major;
    private Integer old_game_version_minor;
    private Integer old_game_version_patch;

    public Integer getAudio_sound() {
        return this.audio_sound;
    }

    public Integer getCtrl_invertmouse() {
        return this.ctrl_invertmouse;
    }

    public Integer getCtrl_islefthanded() {
        return this.ctrl_islefthanded;
    }

    public Float getCtrl_sensitivity() {
        return this.ctrl_sensitivity;
    }

    public Integer getCtrl_usetouchjoypad() {
        return this.ctrl_usetouchjoypad;
    }

    public Integer getCtrl_usetouchscreen() {
        return this.ctrl_usetouchscreen;
    }

    public String getDev_autoloadlevel() {
        return this.dev_autoloadlevel;
    }

    public String getDev_disablefilesystem() {
        return this.dev_disablefilesystem;
    }

    public String getDev_showchunkmap() {
        return this.dev_showchunkmap;
    }

    public Integer getFeedback_vibration() {
        return this.feedback_vibration;
    }

    public Integer getGame_difficulty() {
        return this.game_difficulty;
    }

    public String getGame_flatworldlayers() {
        return this.game_flatworldlayers;
    }

    public String getGame_language() {
        return this.game_language;
    }

    public Integer getGame_lastcustomskin() {
        return this.game_lastcustomskin;
    }

    public String getGame_lastcustomskinnew() {
        return this.game_lastcustomskinnew;
    }

    public String getGame_limitworldsize() {
        return this.game_limitworldsize;
    }

    public Integer getGame_skintype() {
        return this.game_skintype;
    }

    public String getGame_skintypefull() {
        return this.game_skintypefull;
    }

    public Integer getGame_thirdperson() {
        return this.game_thirdperson;
    }

    public Integer getGfx_animatetextures() {
        return this.gfx_animatetextures;
    }

    public Float getGfx_dpadscale() {
        return this.gfx_dpadscale;
    }

    public Integer getGfx_fancygraphics() {
        return this.gfx_fancygraphics;
    }

    public Integer getGfx_fancyskies() {
        return this.gfx_fancyskies;
    }

    public String getGfx_gamma() {
        return this.gfx_gamma;
    }

    public Integer getGfx_guiscale() {
        return this.gfx_guiscale;
    }

    public Integer getGfx_hidegui() {
        return this.gfx_hidegui;
    }

    public Float getGfx_pixeldensity() {
        return this.gfx_pixeldensity;
    }

    public Integer getGfx_renderdistance() {
        return this.gfx_renderdistance;
    }

    public String getGfx_renderdistance_new() {
        return this.gfx_renderdistance_new;
    }

    public Integer getMp_server_visible() {
        return this.mp_server_visible;
    }

    public String getMp_username() {
        return this.mp_username;
    }

    public Integer getOld_game_version_beta() {
        return this.old_game_version_beta;
    }

    public Integer getOld_game_version_major() {
        return this.old_game_version_major;
    }

    public Integer getOld_game_version_minor() {
        return this.old_game_version_minor;
    }

    public Integer getOld_game_version_patch() {
        return this.old_game_version_patch;
    }

    public void setAudio_sound(Integer num) {
        this.audio_sound = num;
    }

    public void setCtrl_invertmouse(Integer num) {
        this.ctrl_invertmouse = num;
    }

    public void setCtrl_islefthanded(Integer num) {
        this.ctrl_islefthanded = num;
    }

    public void setCtrl_sensitivity(Float f) {
        this.ctrl_sensitivity = f;
    }

    public void setCtrl_usetouchjoypad(Integer num) {
        this.ctrl_usetouchjoypad = num;
    }

    public void setCtrl_usetouchscreen(Integer num) {
        this.ctrl_usetouchscreen = num;
    }

    public void setDev_autoloadlevel(String str) {
        this.dev_autoloadlevel = str;
    }

    public void setDev_disablefilesystem(String str) {
        this.dev_disablefilesystem = str;
    }

    public void setDev_showchunkmap(String str) {
        this.dev_showchunkmap = str;
    }

    public void setFeedback_vibration(Integer num) {
        this.feedback_vibration = num;
    }

    public void setGame_difficulty(Integer num) {
        this.game_difficulty = num;
    }

    public void setGame_flatworldlayers(String str) {
        this.game_flatworldlayers = str;
    }

    public void setGame_language(String str) {
        this.game_language = str;
    }

    public void setGame_lastcustomskin(Integer num) {
        this.game_lastcustomskin = num;
    }

    public void setGame_lastcustomskinnew(String str) {
        this.game_lastcustomskinnew = str;
    }

    public void setGame_limitworldsize(String str) {
        this.game_limitworldsize = str;
    }

    public void setGame_skintype(Integer num) {
        this.game_skintype = num;
    }

    public void setGame_skintypefull(String str) {
        this.game_skintypefull = str;
    }

    public void setGame_thirdperson(Integer num) {
        this.game_thirdperson = num;
    }

    public void setGfx_animatetextures(Integer num) {
        this.gfx_animatetextures = num;
    }

    public void setGfx_dpadscale(Float f) {
        this.gfx_dpadscale = f;
    }

    public void setGfx_fancygraphics(Integer num) {
        this.gfx_fancygraphics = num;
    }

    public void setGfx_fancyskies(Integer num) {
        this.gfx_fancyskies = num;
    }

    public void setGfx_gamma(String str) {
        this.gfx_gamma = str;
    }

    public void setGfx_guiscale(Integer num) {
        this.gfx_guiscale = num;
    }

    public void setGfx_hidegui(Integer num) {
        this.gfx_hidegui = num;
    }

    public void setGfx_pixeldensity(Float f) {
        this.gfx_pixeldensity = f;
    }

    public void setGfx_renderdistance(Integer num) {
        this.gfx_renderdistance = num;
    }

    public void setGfx_renderdistance_new(String str) {
        this.gfx_renderdistance_new = str;
    }

    public void setMp_server_visible(Integer num) {
        this.mp_server_visible = num;
    }

    public void setMp_username(String str) {
        this.mp_username = str;
    }

    public void setOld_game_version_beta(Integer num) {
        this.old_game_version_beta = num;
    }

    public void setOld_game_version_major(Integer num) {
        this.old_game_version_major = num;
    }

    public void setOld_game_version_minor(Integer num) {
        this.old_game_version_minor = num;
    }

    public void setOld_game_version_patch(Integer num) {
        this.old_game_version_patch = num;
    }
}
