import {Spieler} from "./spieler";
/**
 * Created by Max on 06.04.2017.
 */

export class Spiel {
    id: number;
    spieler1: Spieler;
    spieler2: Spieler;
    gespieltAm: Date;
    satz1: string;
    satz2: string;
    satz3: string;
    gefordertAm: Date;
    gespielt: boolean;
    gewinner: string;
}