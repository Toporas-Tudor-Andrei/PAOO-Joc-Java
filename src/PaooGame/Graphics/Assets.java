package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/** Clasa incarca fiecare element grafic necesar jocului.
    <p>
    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage top_left_side_fence;
    public static BufferedImage top_left_corner_fence;
    public static BufferedImage top_side_fence;
    public static BufferedImage top_right_corner_fence;
    public static BufferedImage top_right_side_fence;
    public static BufferedImage right_corner_fence;
    public static BufferedImage bot_right_side_fence;
    public static BufferedImage bot_right_corner_fence;
    public static BufferedImage bot_side_fence;
    public static BufferedImage bot_left_corner_fence;
    public static BufferedImage bot_left_side_fence;
    public static BufferedImage left_corner_fence;
    public static BufferedImage floor;
    public static BufferedImage top_left_hole;
    public static BufferedImage top_hole;
    public static BufferedImage top_right_hole;
    public static BufferedImage right_hole;
    public static BufferedImage bot_right_hole;
    public static BufferedImage bot_hole;
    public static BufferedImage bot_left_hole;
    public static BufferedImage left_hole;
    public static BufferedImage dark_hole;

    public static BufferedImage[] Player_idle_right;
    public static BufferedImage[] Player_idle_left;
    public static BufferedImage[] Player_walk_right;
    public static BufferedImage[] Player_walk_left;
    public static BufferedImage[] Player_run_right;
    public static BufferedImage[] Player_run_left;
    public static BufferedImage[] Player_turn_left_right;
    public static BufferedImage[] Player_turn_right_left;
    public static BufferedImage[] Player_damage_right;
    public static BufferedImage[] Player_damage_left;
    public static BufferedImage[] Player_death_right;
    public static BufferedImage[] Player_death_left;
    public static BufferedImage[][] Player_animations;

    public static BufferedImage[] enemy1;
    public static BufferedImage[] enemy2;
    public static BufferedImage[] enemy3;
    public static BufferedImage[] enemy4;
    public static BufferedImage[] enemy5;
    public static BufferedImage[] enemy6;
    public static BufferedImage[] enemy7;
    public static BufferedImage[] enemy8;
    public static BufferedImage[] enemy9;
    public static BufferedImage[][] enemies;

    public static BufferedImage[] portal;

    /** Functia initializaza referintele catre elementele grafice utilizate.
        <p>
        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/fence_dungeon_tiles_v2-1.png"));
        SpriteSheet PlayerSprite = new SpriteSheet(ImageLoader.LoadImage("/textures/Player_SpriteSheet.png"));
        SpriteSheet EnemySprite = new SpriteSheet(ImageLoader.LoadImage("/textures/Enemies_Sprite_sheet.png"));
        SpriteSheet Portal = new SpriteSheet(ImageLoader.LoadImage("/textures/portal_sprite.png"));

            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        floor = sheet.cropTile(3, 3);
        left_corner_fence = sheet.cropTile(2, 3);
        bot_left_side_fence = sheet.cropTile(1, 3);
        bot_left_corner_fence = sheet.cropTile(0, 3);
        bot_right_hole = sheet.cropTile(5, 2);
        bot_hole = sheet.cropTile(4, 2);
        bot_left_hole = sheet.cropTile(3, 2);
        bot_side_fence = sheet.cropTile(2, 2);
        bot_right_corner_fence = sheet.cropTile(1, 2);
        bot_right_side_fence = sheet.cropTile(0, 2);
        right_hole = sheet.cropTile(5, 1);
        dark_hole = sheet.cropTile(4, 1);
        left_hole = sheet.cropTile(3, 1);
        right_corner_fence = sheet.cropTile(2, 1);
        top_right_side_fence = sheet.cropTile(1, 1);
        top_right_corner_fence = sheet.cropTile(0, 1);
        top_right_hole = sheet.cropTile(5, 0);
        top_hole = sheet.cropTile(4, 0);
        top_left_hole = sheet.cropTile(3, 0);
        top_side_fence = sheet.cropTile(2, 0);
        top_left_corner_fence = sheet.cropTile(1, 0);
        top_left_side_fence = sheet.cropTile(0, 0);


        Player_idle_right = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,0),
                PlayerSprite.cropPlayer(1,0),
                PlayerSprite.cropPlayer(2,0),
                PlayerSprite.cropPlayer(3,0)};
        Player_idle_left = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,1),
                PlayerSprite.cropPlayer(1,1),
                PlayerSprite.cropPlayer(2,1),
                PlayerSprite.cropPlayer(3,1)};
        Player_walk_right = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,2),
                PlayerSprite.cropPlayer(1,2),
                PlayerSprite.cropPlayer(2,2),
                PlayerSprite.cropPlayer(3,2)};
        Player_walk_left = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,4),
                PlayerSprite.cropPlayer(1,4),
                PlayerSprite.cropPlayer(2,4),
                PlayerSprite.cropPlayer(3,4)};
        Player_run_right = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,3),
                PlayerSprite.cropPlayer(1,3),
                PlayerSprite.cropPlayer(2,3),
                PlayerSprite.cropPlayer(3,3)};
        Player_run_left = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,5),
                PlayerSprite.cropPlayer(1,5),
                PlayerSprite.cropPlayer(2,5),
                PlayerSprite.cropPlayer(3,5)};
        Player_turn_left_right = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,7),
                PlayerSprite.cropPlayer(1,7),
                PlayerSprite.cropPlayer(2,7),
                PlayerSprite.cropPlayer(3,7),
                PlayerSprite.cropPlayer(4,7)};
        Player_turn_right_left = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,6),
                PlayerSprite.cropPlayer(1,6),
                PlayerSprite.cropPlayer(2,6),
                PlayerSprite.cropPlayer(3,6),
                PlayerSprite.cropPlayer(4,6)};
        Player_damage_right = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,8),
                PlayerSprite.cropPlayer(1,8),
                PlayerSprite.cropPlayer(2,8),
                PlayerSprite.cropPlayer(3,8)};
        Player_damage_left = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,9),
                PlayerSprite.cropPlayer(1,9),
                PlayerSprite.cropPlayer(2,9),
                PlayerSprite.cropPlayer(3,9)};
        Player_death_right = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,10),
                PlayerSprite.cropPlayer(1,10),
                PlayerSprite.cropPlayer(2,10),
                PlayerSprite.cropPlayer(3,10)};
        Player_death_left = new BufferedImage[]{
                PlayerSprite.cropPlayer(0,11),
                PlayerSprite.cropPlayer(1,11),
                PlayerSprite.cropPlayer(2,11),
                PlayerSprite.cropPlayer(3,11)};

        Player_animations = new BufferedImage[][]{
                Player_idle_right,
                Player_idle_left,
                Player_walk_right,
                Player_run_right,
                Player_walk_left,
                Player_run_left,
                Player_turn_right_left,
                Player_turn_left_right,
                Player_damage_right,
                Player_damage_left,
                Player_death_right,
                Player_death_left};

        enemy1 = new BufferedImage[]{EnemySprite.crope(0,0),
                                     EnemySprite.crope(1,0),
                                     EnemySprite.crope(2,0),
                                     EnemySprite.crope(3,0)};

        enemy2 = new BufferedImage[]{EnemySprite.crope(0,1),
                                     EnemySprite.crope(1,1),
                                     EnemySprite.crope(2,1),
                                     EnemySprite.crope(3,1)};

        enemy3 = new BufferedImage[]{EnemySprite.crope(0,2),
                                     EnemySprite.crope(1,2),
                                     EnemySprite.crope(2,2),
                                     EnemySprite.crope(3,2)};

        enemy4 = new BufferedImage[]{EnemySprite.crope(0,3),
                                     EnemySprite.crope(1,3),
                                     EnemySprite.crope(2,3),
                                     EnemySprite.crope(3,3)};

        enemy5 = new BufferedImage[]{EnemySprite.crope(0,4),
                                     EnemySprite.crope(1,4),
                                     EnemySprite.crope(2,4),
                                     EnemySprite.crope(3,4)};

        enemy6 = new BufferedImage[]{EnemySprite.crope(0,5),
                                     EnemySprite.crope(1,5),
                                     EnemySprite.crope(2,5),
                                     EnemySprite.crope(3,5)};

        enemy7 = new BufferedImage[]{EnemySprite.crope(0,6),
                                     EnemySprite.crope(1,6),
                                     EnemySprite.crope(2,6),
                                     EnemySprite.crope(3,6)};

        enemy8 = new BufferedImage[]{EnemySprite.crope(0,7),
                                     EnemySprite.crope(1,7),
                                     EnemySprite.crope(2,7),
                                     EnemySprite.crope(3,7)};

        enemy9 = new BufferedImage[]{EnemySprite.crope(0,8),
                                     EnemySprite.crope(1,8),
                                     EnemySprite.crope(2,8),
                                     EnemySprite.crope(3,8)};

        enemies = new BufferedImage[][]{enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, enemy8, enemy9};

        portal = new BufferedImage[]{Portal.cropPortal(0,0),
                                     Portal.cropPortal(1,0),
                                     Portal.cropPortal(2,0),
                                     Portal.cropPortal(3,0),
                                     Portal.cropPortal(0,1),
                                     Portal.cropPortal(1,1),
                                     Portal.cropPortal(2,1),
                                     Portal.cropPortal(3,1),
                                     Portal.cropPortal(0,2),
                                     Portal.cropPortal(1,2),
                                     Portal.cropPortal(2,2),
                                     Portal.cropPortal(3,2),
                                     Portal.cropPortal(0,3)};
    }


}
