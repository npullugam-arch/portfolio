import {createContext,useContext} from 'react'; import type {SectionId} from './data/portfolio';
export type AppState={section:SectionId;setSection:(s:SectionId)=>void;project:number|null;setProject:(n:number|null)=>void;loaded:boolean;setLoaded:(v:boolean)=>void};
export const AppContext=createContext<AppState|null>(null); export const useApp=()=>{const value=useContext(AppContext);if(!value)throw Error('App context missing');return value};
