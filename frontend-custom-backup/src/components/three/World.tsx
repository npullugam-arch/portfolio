import {ContactShadows,Text,useAnimations,useGLTF} from '@react-three/drei';
import {useFrame} from '@react-three/fiber';
import {useEffect,useRef} from 'react';
import {Group,Mesh,MeshStandardMaterial} from 'three';

const cream='#f5efe6', ink='#2d2a24';

function Box({position,scale,color='#fffdf8',rotation=[0,0,0]}:{position:[number,number,number],scale:[number,number,number],color?:string,rotation?:[number,number,number]}){
 return <mesh position={position} rotation={rotation} castShadow receiveShadow><boxGeometry args={scale}/><meshStandardMaterial color={color} roughness={.75}/></mesh>;
}

function Monitor({x,code=false}:{x:number,code?:boolean}){
 const cursor=useRef<Mesh>(null);
 useFrame(({clock})=>{if(cursor.current)cursor.current.visible=Math.sin(clock.elapsedTime*4)>0});
 return <group position={[x,2.25,-.2]}><Box position={[0,0,0]} scale={[2.05,1.42,.16]} color="#3d3d47"/><mesh position={[0,0,.09]}><planeGeometry args={[1.82,1.18]}/><meshStandardMaterial color={code?'#27303a':'#343b43'}/></mesh>{Array.from({length:7}).map((_,i)=><Box key={i} position={[-.58,.43-i*.135,.105]} scale={[.4+(i%3)*.22,.035,.02]} color={code?(i%3===0?'#f0a236':i%3===1?'#b479d4':'#9ac94a'):'#8b969d'}/>)}<mesh ref={cursor} position={[.55,-.42,.11]}><planeGeometry args={[.035,.13]}/><meshBasicMaterial color="#ffe17c"/></mesh><Box position={[0,-.94,0]} scale={[.13,.48,.13]} color="#45434c"/><Box position={[0,-1.16,0]} scale={[.72,.1,.38]} color="#45434c"/></group>;
}

function Plant(){return <group position={[4.1,-.22,.4]}><mesh castShadow><cylinderGeometry args={[.55,.42,.72,20]}/><meshStandardMaterial color="#c8b29b"/></mesh>{[-.65,-.3,0,.3,.65].map((a,i)=><mesh key={i} position={[Math.sin(a)*.38,.85+Math.cos(a)*.25,Math.cos(a)*.16]} rotation={[0,0,a]} castShadow><capsuleGeometry args={[.18,.65,8,16]}/><meshStandardMaterial color={i%2?'#74be3d':'#96db33'} roughness={.72}/></mesh>)}</group>}

function Workstation(){return <group><Box position={[1,3.7,-1.75]} scale={[10,6.6,.12]} color="#efe8dc"/><Box position={[-.3,4.75,-1.6]} scale={[2.7,.14,.5]} color="#d9b776"/><Box position={[-1.05,5.12,-1.58]} scale={[.3,.68,.36]} color="#ec9b24"/><Box position={[-.65,5.12,-1.58]} scale={[.3,.68,.36]} color="#7288a7"/><Box position={[2.8,4.2,-1.58]} scale={[2.2,1.45,.12]} color="#a88972"/><Box position={[2.3,4.3,-1.49]} scale={[.55,.42,.02]} color="#cee4f4" rotation={[0,0,-.08]}/><Box position={[3.35,3.95,-1.49]} scale={[.55,.42,.02]} color="#fffdf8" rotation={[0,0,.08]}/><Box position={[5.05,4.25,-1.57]} scale={[1.5,1.5,.13]} color="#8bb8dc"/><mesh position={[5.05,4.25,-1.48]}><circleGeometry args={[.42,28]}/><meshStandardMaterial color="#f9dd87"/></mesh><Box position={[1,.8,.3]} scale={[5.8,.28,2.05]}/>{[-1.5,3.5].map(x=><Box key={x} position={[x,-.72,.65]} scale={[.25,3,.25]} color="#c79a64"/>)}<Monitor x={-.25}/><Monitor x={2.05} code/><Box position={[.85,.98,1]} scale={[1.65,.09,.55]} color="#d6d7d8"/><Box position={[2.15,.98,1.05]} scale={[.42,.08,.55]} color="#d6d7d8"/><Box position={[1,-.88,.75]} scale={[5.6,.08,3.6]} color="#eca52e"/><Box position={[1,-.82,.78]} scale={[4.7,.04,2.75]} color="#f8dc72"/><Plant/><Text position={[-3.25,3.35,-1.62]} fontSize={.2} color={ink}>IDEAS → CODE → IMPACT</Text></group>}

function RiggedDeveloper({scroll}:{scroll:number}){
 const root=useRef<Group>(null);
 const {scene,animations}=useGLTF('/portfolio/models/developer.glb');
 const {actions}=useAnimations(animations,scene);
 const walking=scroll>.62&&scroll<1.58;
 useEffect(()=>{scene.traverse(object=>{const mesh=object as Mesh;if(mesh.isMesh){mesh.castShadow=true;mesh.receiveShadow=true;const material=mesh.material as MeshStandardMaterial;if(material){material.roughness=.82;material.metalness=0}}})},[scene]);
 useEffect(()=>{const active=actions[walking?'Walk':'Idle'];Object.values(actions).forEach(action=>action?.fadeOut(.25));active?.reset().fadeIn(.25).play();return()=>{active?.fadeOut(.2)}},[actions,walking]);
 useFrame(()=>{const p=Math.min(1,Math.max(0,(scroll-.58)/1.08));if(root.current){root.current.position.set(1.05-2*p,-.78+Math.sin(p*Math.PI)*.35,1.6+1.15*p);root.current.rotation.y=Math.PI*(1-p);root.current.scale.setScalar(.72+p*.2)}});
 return <group ref={root} position={[1.05,-.78,1.6]} rotation={[0,Math.PI,0]} scale={.72}><primitive object={scene}/></group>;
}

function Hologram(){return <group position={[-.95,-.8,2.72]}><mesh rotation={[-Math.PI/2,0,0]}><cylinderGeometry args={[1.05,1.25,.25,40]}/><meshStandardMaterial color="#0086bb" emissive="#34bfff" emissiveIntensity={1.5}/></mesh>{[0,.16,.32].map(y=><mesh key={y} position={[0,y,0]} rotation={[Math.PI/2,0,0]}><torusGeometry args={[.9+y*.2,.025,8,48]}/><meshBasicMaterial color="#34bfff" transparent opacity={.75}/></mesh>)}</group>}

export function World({mobile,scroll}:{mobile:boolean,scroll:number}){
 const room=useRef<Group>(null),scene=useRef<Group>(null);const about=scroll>1.38&&scroll<3.5;
 useFrame(({pointer})=>{if(scene.current){scene.current.rotation.y+=(pointer.x*.014-scene.current.rotation.y)*.025;scene.current.rotation.x+=(-pointer.y*.007-scene.current.rotation.x)*.025}if(room.current){room.current.position.y+=(Math.min(0,-Math.max(0,scroll-.55)*4)-room.current.position.y)*.04;room.current.visible=scroll<1.62}});
 return <><color attach="background" args={[about?'#052e87':cream]}/><ambientLight intensity={about?1.4:2.3}/><hemisphereLight args={[about?'#34bfff':'#fff8e8',about?'#002474':'#d7c4a8',2]}/><directionalLight castShadow={!mobile} position={[-4,9,7]} intensity={3} color={about?'#73dcff':'#fff4dc'} shadow-mapSize={[mobile?512:1024,mobile?512:1024]}/><group ref={scene} position={[mobile?0:2.7,-1.08,0]} scale={mobile?.72:1}><group ref={room}><Workstation/></group><RiggedDeveloper scroll={scroll}/>{about&&<Hologram/>}</group>{!about&&<ContactShadows position={[mobile?0:2.7,-2.02,0]} opacity={.25} scale={13} blur={2.8} far={6} color="#9f7b55"/>}</>;
}
useGLTF.preload('/portfolio/models/developer.glb');
