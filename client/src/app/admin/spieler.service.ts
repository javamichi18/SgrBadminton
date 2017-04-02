import {Injectable} from "@angular/core";
import {Spieler} from "../spieler";
import {SPIELER} from "../mock-spieler";


@Injectable()
export class SpielerService {

    public getTitle() {
        return 'Hi from my-service.service.ts'
    }

    public  getSpielerPlain(): Spieler[] {

        return SPIELER;
    }

    public  getSpieler(): Promise<Spieler[]> {

        return Promise.resolve(SPIELER);
    }

    public getSpielerDetails(id: number): Promise<Spieler> {

        return this.getSpieler()
            .then(spieler => spieler.find(s => s.id === id));
    }

    public getSpielerDetailsPlain(id: number): Spieler {

        return this.getSpielerPlain().find(s => s.id === id);
    }

    constructor() {
    }

}
