import { reactive } from "vue"; import { portfolioApi,type ProfileData,type SkillData,type ProjectData,type ContactData } from "./portfolioApi";
export const portfolio=reactive<{profile:ProfileData;skills:SkillData[];projects:ProjectData[];contact:ContactData;ready:boolean;projectsLoaded:boolean}>({profile:{fullName:"Nanda Kishore",professionalTitle:"AI Engineer / Full Stack Developer",country:"India"},skills:[],projects:[],contact:{email:"nandakishore@example.com"},ready:false,projectsLoaded:false});
export async function loadPortfolio(){
 const requests=[
  portfolioApi.profile().then(profile=>{portfolio.profile=profile;document.title=`${profile.fullName} - ${profile.professionalTitle || "Developer"}`}),
  portfolioApi.skills().then(skills=>portfolio.skills=skills),
  portfolioApi.projects().then(projects=>{portfolio.projects=projects;portfolio.projectsLoaded=true}),
  portfolioApi.contact().then(contact=>portfolio.contact=contact),
 ];
 const results=await Promise.allSettled(requests);
 results.filter(result=>result.status==="rejected").forEach(result=>console.warn("Portfolio content API unavailable; bundled content remains active.",(result as PromiseRejectedResult).reason));
 portfolio.ready=true;
}
export function contactHref(type:keyof ContactData,value?:string){if(!value)return "";if(type==="email")return `mailto:${value}`;if(type==="phone")return `tel:${value}`;if(type==="whatsapp")return /^https?:/.test(value)?value:`https://wa.me/${value.replace(/\D/g,"")}`;return value;}
